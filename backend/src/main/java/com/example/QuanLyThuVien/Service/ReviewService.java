package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.ReviewDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import com.example.QuanLyThuVien.Entity.Review;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.BorrowrequestRepository;
import com.example.QuanLyThuVien.Repo.MemberRepository;
import com.example.QuanLyThuVien.Repo.ReviewRepository;
import com.example.QuanLyThuVien.Entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public String createReview(ReviewDto reviewDTO) {
        try {

            Borrowrequest borrowRequest = borrowrequestRepository.findById(reviewDTO.getBorrowRequestId())
                    .orElseThrow(() -> new IllegalArgumentException("Borrow request not found"));
            Member member = memberRepository.findById(reviewDTO.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("Member not found"));


            Review review = new Review();
            review.setBorrowRequestId(borrowRequest);
            review.setMemberID(member);
            review.setRating(reviewDTO.getRating());
            review.setComment(reviewDTO.getComment());
           review.setReviewDate(reviewDTO.getReviewDate());
            reviewRepository.save(review);

            return "Review created successfully!";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: Unable to create review";
        }
    }
    public List<Review> getReviewsByBookId(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
