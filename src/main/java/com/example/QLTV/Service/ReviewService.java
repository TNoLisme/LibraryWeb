package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.ReviewDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Review;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.MemberRepository;
import com.example.QuanLyThuVien.Repo.ReviewRepository;
import com.example.QuanLyThuVien.Entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    // Tạo mới Review
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setBookID(bookRepository.getReferenceById(reviewDto.getBookID()));  // Thiết lập đối tượng Book từ ID
        review.setMemberID(memberRepository.getReferenceById(reviewDto.getMemberID()));  // Thiết lập đối tượng Member từ ID
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setReviewDate(reviewDto.getReviewDate());

        Review savedReview = reviewRepository.save(review);
        return new ReviewDto(
                savedReview.getId(),
                savedReview.getBookID().getId(),
                savedReview.getMemberID().getId(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getReviewDate()
        );
    }

    // Cập nhật Review
    public ReviewDto updateReview(Integer reviewId, ReviewDto reviewDto) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setRating(reviewDto.getRating());
            review.setComment(reviewDto.getComment());
            review.setReviewDate(reviewDto.getReviewDate());
            Review updatedReview = reviewRepository.save(review);

            return new ReviewDto(
                    updatedReview.getId(),
                    updatedReview.getBookID().getId(),
                    updatedReview.getMemberID().getId(),
                    updatedReview.getRating(),
                    updatedReview.getComment(),
                    updatedReview.getReviewDate()
            );
        }
        return null;  // Trả về null nếu không tìm thấy Review
    }

    // Xóa Review
    public void deleteReview(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // Lấy tất cả Review
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
       return reviews;
    }

    // Tìm Review theo Member ID
    public List<ReviewDto> getReviewsByMemberId(Integer memberId) {
        List<Review> reviews = reviewRepository.findByMemberID_Id(memberId);
        return reviews.stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getBookID().getId(),
                        review.getMemberID().getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getReviewDate()
                ))
                .collect(Collectors.toList());
    }
}
