package com.example.QuanLyThuVien.Controller;

import com.example.QuanLyThuVien.DTO.BorrowrequestDto;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Service.BorrowrequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/borrowrequests")
public class BorrowrequestController {

    private final BorrowrequestService borrowrequestService;

    @Autowired
    public BorrowrequestController(BorrowrequestService borrowrequestService) {
        this.borrowrequestService = borrowrequestService;
    }

    // Lấy tất cả yêu cầu mượn sách
    @GetMapping
    public List<BorrowrequestDto> getAllBorrowrequests() {
        return borrowrequestService.getAllBorrowrequests();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Borrowrequest> getBorrowrequestById(@PathVariable Integer id) {
        Optional<Borrowrequest> borrowrequest = borrowrequestService.getBorrowrequestById(id);
        return borrowrequest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới yêu cầu mượn sách
    @PostMapping
    public ResponseEntity<Borrowrequest> createBorrowrequest(@RequestBody BorrowrequestDto borrowrequestDto) {
        try {
            if(borrowrequestService.createBorrowrequest(borrowrequestDto)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Cập nhật yêu cầu mượn sách
    @PutMapping("/{id}")
    public ResponseEntity<Borrowrequest> updateBorrowrequest(@PathVariable Integer id, @RequestBody BorrowrequestDto borrowrequestDto) {
        try {
            Borrowrequest updatedRequest = borrowrequestService.updateBorrowrequest(id, borrowrequestDto);
            return ResponseEntity.ok(updatedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa yêu cầu mượn sách
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowrequest(@PathVariable Integer id) {
        try {
            borrowrequestService.deleteBorrowrequest(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/members")
    public List<BorrowrequestDto> getBorrowRequestsNotInReviews(@RequestParam Integer memberId) {
        return borrowrequestService.getBorrowRequestsNotInReviewsByMemberId(memberId);
    }

}
