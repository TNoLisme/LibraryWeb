package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BorrowrequestDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Entity.Fine;
import com.example.QuanLyThuVien.Entity.Member;
import com.example.QuanLyThuVien.Entity.MemberPenalties;
import com.example.QuanLyThuVien.Entity.MemberPenalties.PaidStatus;
import com.example.QuanLyThuVien.Repo.BorrowrequestRepository;
import com.example.QuanLyThuVien.Repo.FineRepository;
import com.example.QuanLyThuVien.Repo.MemberPenaltiesRepository;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowrequestService {

    private final BorrowrequestRepository borrowrequestRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private FineRepository fineRepository;
    private MemberPenaltiesRepository memberPenaltiesRepository;


    @Autowired
    public BorrowrequestService(BorrowrequestRepository borrowrequestRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.borrowrequestRepository = borrowrequestRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public boolean createBorrowrequest(BorrowrequestDto borrowrequestDto) {
        try {
            // Check if the book exists in the database
            Optional<Book> bookOptional = bookRepository.findById(borrowrequestDto.getBookID());
            if (bookOptional.isPresent()) {
                Borrowrequest borrowrequest = new Borrowrequest();
                borrowrequest.setBookID(bookRepository.getReferenceById(borrowrequestDto.getBookID()));
                borrowrequest.setMemberID(memberRepository.getReferenceById(borrowrequestDto.getMemID()));
                
                // Ensure the borrowDate and returnDate are set properly
                borrowrequest.setBorrowDate(borrowrequestDto.getBorrowDate());  // Make sure this is set
                borrowrequest.setReturnDate(borrowrequestDto.getReturnDate());
                borrowrequest.setStatus("pending");  // Default status

                // Save the borrowrequest object
                borrowrequestRepository.save(borrowrequest);
                return true;
            } else {
                throw new RuntimeException("Book not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace for debugging
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
    // Cập nhật yêu cầu mượn sách
    public Borrowrequest updateBorrowrequest(Integer id, BorrowrequestDto borrowrequestDto) {
        // Lấy thông tin mượn sách theo ID
        Borrowrequest borrowrequest = borrowrequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow request not found."));
        
     
        Member member = memberRepository.findById(borrowrequest.getMemberID()) 
            .orElseThrow(() -> new RuntimeException("Member not found"));



        // Lấy thông tin sách liên quan đến yêu cầu
        Book book = borrowrequest.getBookID();

        // Nếu trạng thái yêu cầu thay đổi, xử lý số lượng sách
        if (!borrowrequest.getStatus().equals(borrowrequestDto.getStatus())) {
            switch (borrowrequestDto.getStatus()) {
                case "borrowed": // Nếu trạng thái chuyển sang mượn sách
                    if (book.getQuantity() > 0) {
                        book.setQuantity(book.getQuantity() - 1);
                    } else {
                        throw new RuntimeException("Book is out of stock.");
                    }
                    break;

                case "returned": 
                    book.setQuantity(book.getQuantity() + 1);
                    break;

                case "lost": 
                case "damaged": 
                case "overdue":
                    // Lấy lý do phạt tương ứng từ fineRepository
                    Fine fine = fineRepository.findByFineReason(borrowrequestDto.getStatus())
                            .orElseThrow(() -> new RuntimeException("Fine reason not found"));

                    // Tạo bản ghi phạt cho thành viên
                    MemberPenalties memberPenalty = new MemberPenalties();
                    memberPenalty.setMember(member); // Sử dụng đối tượng Member thay vì MemberID
                    memberPenalty.setFine(fine); // Sử dụng đối tượng Fine thay vì FineID
                    memberPenalty.setPenaltyDate(LocalDate.now()); // Ngày phạt là ngày hiện tại
                    memberPenalty.setPaidStatus(PaidStatus.UNPAID); // Sử dụng enum PaidStatus.UNPAID cho trạng thái thanh toán

                    memberPenaltiesRepository.save(memberPenalty);
                    break;

                default:
                    break;
            }

            bookRepository.save(book);
        }

        borrowrequest.setBorrowDate(borrowrequestDto.getBorrowDate());
        borrowrequest.setReturnDate(borrowrequestDto.getReturnDate());
        borrowrequest.setStatus(borrowrequestDto.getStatus());

        return borrowrequestRepository.save(borrowrequest);
    }




    // Xóa yêu cầu mượn sách
    public void deleteBorrowrequest(Integer id) {
        Borrowrequest borrowrequest = borrowrequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow request not found."));
        borrowrequestRepository.delete(borrowrequest);
    }
    public List<Borrowrequest> getBorrowRequestsNotInReviewsByMemberId(Integer memberId) {
        return borrowrequestRepository.findBorrowRequestsNotInReviewsByMemberId(memberId);
    }
}
