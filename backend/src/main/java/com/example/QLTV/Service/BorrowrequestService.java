package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BorrowrequestDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Repo.BorrowrequestRepository;
import com.example.QuanLyThuVien.Repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowrequestService {

    private final BorrowrequestRepository borrowrequestRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BorrowrequestService(BorrowrequestRepository borrowrequestRepository, BookRepository bookRepository) {
        this.borrowrequestRepository = borrowrequestRepository;
        this.bookRepository = bookRepository;
    }

    // Thêm mới yêu cầu mượn sách
    public Borrowrequest createBorrowrequest(BorrowrequestDto borrowrequestDto) {
        Optional<Book> bookOptional = bookRepository.findById(borrowrequestDto.getBookID());
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Borrowrequest borrowrequest = new Borrowrequest();
            borrowrequest.setBookID(bookRepository.getReferenceById(borrowrequestDto.getBookID()));
            borrowrequest.setBorrowDate(borrowrequestDto.getBorrowDate());
            borrowrequest.setReturnDate(borrowrequestDto.getReturnDate());
            borrowrequest.setStatus("PENDING"); // Mặc định yêu cầu mượn có trạng thái PENDING
            return borrowrequestRepository.save(borrowrequest);
        } else {
            throw new RuntimeException("Book not found.");
        }
    }

    // Lấy tất cả yêu cầu mượn sách
    public List<Borrowrequest> getAllBorrowrequests() {
        return borrowrequestRepository.findAll();
    }

    // Lấy yêu cầu mượn sách theo ID
    public Optional<Borrowrequest> getBorrowrequestById(Integer id) {
        return borrowrequestRepository.findById(id);
    }

    // Cập nhật yêu cầu mượn sách
    public Borrowrequest updateBorrowrequest(Integer id, BorrowrequestDto borrowrequestDto) {
        Borrowrequest borrowrequest = borrowrequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow request not found."));
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
}
