package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BorrowrequestDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Entity.Fine;
import com.example.QuanLyThuVien.Entity.Member;
import com.example.QuanLyThuVien.Entity.MemberPenalties;
import com.example.QuanLyThuVien.Repo.BorrowrequestRepository;
import com.example.QuanLyThuVien.Repo.FineRepository;
import com.example.QuanLyThuVien.Repo.MemberPenaltiesRepository;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.MemberRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowrequestService {
	 @Autowired
    private final BorrowrequestRepository borrowrequestRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private MemberPenaltiesRepository memberPenaltiesRepository;

    @Autowired
    public BorrowrequestService(BorrowrequestRepository borrowrequestRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.borrowrequestRepository = borrowrequestRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public boolean createBorrowrequest(BorrowrequestDto borrowrequestDto) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(borrowrequestDto.getBookID());
            if (bookOptional.isPresent()) {
                Borrowrequest borrowrequest = new Borrowrequest();
                borrowrequest.setBookID(bookRepository.getReferenceById(borrowrequestDto.getBookID()));
                borrowrequest.setMemberID(memberRepository.getReferenceById(borrowrequestDto.getMemID()));
                
                borrowrequest.setBorrowDate(borrowrequestDto.getBorrowDate()); 
                borrowrequest.setReturnDate(borrowrequestDto.getReturnDate());
                borrowrequest.setStatus("pending");  

                borrowrequestRepository.save(borrowrequest);
                return true;
            } else {
                throw new RuntimeException("Book not found.");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }


    // Lấy tất cả yêu cầu mượn sách
    public List<BorrowrequestDto> getAllBorrowrequests() {
        return borrowrequestRepository.findAllBorrowRequestsAsDto();
    }

    // Lấy yêu cầu mượn sách theo ID
    public Optional<Borrowrequest> getBorrowrequestById(Integer id) {
        return borrowrequestRepository.findById(id);
    }
    public List<Borrowrequest> getBorrowByUserID() {
        return borrowrequestRepository.findAll();
    }
@Transactional
public Borrowrequest updateBorrowrequest(Integer id, BorrowrequestDto borrowrequestDto) {
    Borrowrequest borrowrequest = borrowrequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrow request not found."));

    Book book = borrowrequest.getBookID();
    Member member = borrowrequest.getMemberID();

    // Nếu trạng thái yêu cầu thay đổi, xử lý số lượng sách và bảng phạt
    if (!borrowrequest.getStatus().equals(borrowrequestDto.getStatus())) {
        try {
            String currentStatus = borrowrequest.getStatus();
            String newStatus = borrowrequestDto.getStatus();

            // Nếu trạng thái cũ là "pending" và trạng thái mới là "borrowed"
            if ("pending".equals(currentStatus) && "borrowed".equals(newStatus)) {
                // Trừ sách đi khi bắt đầu mượn sách
                if (book.getQuantity() <= 0) {
                    throw new RuntimeException("Book is out of stock.");
                }
                book.setQuantity(book.getQuantity() - 1);
                bookRepository.save(book);
            } else if ("borrowed".equals(currentStatus)) {
                // Xử lý các trạng thái từ "borrowed"
                switch (newStatus) {
                    case "returned":
                        book.setQuantity(book.getQuantity() + 1);
                        bookRepository.save(book);
                        break;
                    case "overdue":
                        Fine overdueFine = fineRepository.findById(1)
                                .orElseThrow(() -> new RuntimeException("Fine for overdue book not found."));
                        MemberPenalties overduePenalty = new MemberPenalties();
                        overduePenalty.setMember(member);
                        overduePenalty.setFine(overdueFine);
                        overduePenalty.setPenaltyDate(LocalDate.now());
                        overduePenalty.setPaidStatus(MemberPenalties.PaidStatus.unpaid);
                        memberPenaltiesRepository.save(overduePenalty);
                        break;
                    case "lost":
                        Fine lostFine = fineRepository.findById(3)
                                .orElseThrow(() -> new RuntimeException("Fine for lost book not found."));
                        MemberPenalties lostPenalty = new MemberPenalties();
                        lostPenalty.setMember(member);
                        lostPenalty.setFine(lostFine);
                        lostPenalty.setPenaltyDate(LocalDate.now());
                        lostPenalty.setPaidStatus(MemberPenalties.PaidStatus.unpaid);
                        memberPenaltiesRepository.save(lostPenalty);
                        break;
                    case "damaged":
                        Fine damagedFine = fineRepository.findById(2)
                                .orElseThrow(() -> new RuntimeException("Fine for damaged book not found."));
                        MemberPenalties damagedPenalty = new MemberPenalties();
                        damagedPenalty.setMember(member);
                        damagedPenalty.setFine(damagedFine);
                        damagedPenalty.setPenaltyDate(LocalDate.now());
                        damagedPenalty.setPaidStatus(MemberPenalties.PaidStatus.unpaid);
                        memberPenaltiesRepository.save(damagedPenalty);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error saving penalty: " + e.getMessage());
            throw new RuntimeException("Error updating borrow request and saving penalty.");
        }
    }

    // Cập nhật các thông tin khác của yêu cầu mượn
    borrowrequest.setBorrowDate(borrowrequestDto.getBorrowDate());
    borrowrequest.setReturnDate(borrowrequestDto.getReturnDate());
    borrowrequest.setStatus(borrowrequestDto.getStatus());

    // Lưu lại yêu cầu mượn sách đã cập nhật
    return borrowrequestRepository.save(borrowrequest);
}

    // Xóa yêu cầu mượn sách
    public void deleteBorrowrequest(Integer id) {
        Borrowrequest borrowrequest = borrowrequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow request not found."));
        borrowrequestRepository.delete(borrowrequest);
    }
    public List<BorrowrequestDto> getBorrowRequestsNotInReviewsByMemberId(Integer memberId) {
        return borrowrequestRepository.findBorrowRequestsNotInReviewsByMemberId(memberId);
    }

}
