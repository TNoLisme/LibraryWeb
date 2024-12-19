package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.ReviewDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Entity.Review;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.BorrowrequestRepository;
import com.example.QuanLyThuVien.Repo.MemberRepository;
import com.example.QuanLyThuVien.Repo.ReviewRepository;

import jakarta.transaction.Transactional;

import com.example.QuanLyThuVien.Entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BorrowrequestRepository borrowrequestRepository;

    @Autowired
    private MemberRepository memberRepository;
   
    @Transactional
    public String createReview(ReviewDto reviewDTO) {
        try {
            // Kiểm tra xem borrowRequestId và memberId có null không
            System.out.println("Review DTO: " + reviewDTO);  // Log để kiểm tra dữ liệu đầu vào

            if (reviewDTO.getBorrowRequestId() == null || reviewDTO.getMemberId() == null) {
                throw new IllegalArgumentException("Borrow request ID and Member ID cannot be null");
            }

            Borrowrequest borrowRequest = borrowrequestRepository.findById(reviewDTO.getBorrowRequestId())
                    .orElseThrow(() -> new IllegalArgumentException("Borrow request not found"));
            Member member = memberRepository.findById(reviewDTO.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("Member not found"));

            Review review = new Review();
            review.setBorrowRequestId(borrowRequest);
            review.setMemberID(member);
            review.setRating(reviewDTO.getRating());
            review.setComment(reviewDTO.getComment());
            review.setReviewDate(reviewDTO.getReviewDate() != null ? reviewDTO.getReviewDate() : new Date());

            // Log để kiểm tra dữ liệu
            System.out.println("Saving review: " + review);

            // Trước khi lưu, đảm bảo rằng ID của đối tượng là null (tức là đối tượng mới)
            reviewRepository.save(review);
            System.out.println("Review saved successfully with ID: " + review.getId());

            return "Review created successfully!";
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Log thêm lỗi
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace(); // Log thêm lỗi
            return "Error: Unable to create review";
        }
    }




    public List<ReviewDto> getReviewsByBookId(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
