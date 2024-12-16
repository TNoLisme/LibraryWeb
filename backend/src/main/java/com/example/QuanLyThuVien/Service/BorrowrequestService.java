package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BorrowrequestDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Repo.BorrowrequestRepository;
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
        Borrowrequest borrowrequest = borrowrequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow request not found."));
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

                case "returned": // Nếu trạng thái chuyển sang trả sách
                    book.setQuantity(book.getQuantity() + 1);
                    break;

                default:
                    // Không thay đổi số lượng sách cho các trạng thái khác
                    break;
            }

            // Cập nhật thông tin sách
            bookRepository.save(book);
        }

        // Cập nhật các thông tin khác của yêu cầu mượn
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
