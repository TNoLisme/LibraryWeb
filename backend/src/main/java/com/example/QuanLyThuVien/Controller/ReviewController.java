package com.example.QuanLyThuVien.Controller;

import com.example.QuanLyThuVien.DTO.ReviewDto;
import com.example.QuanLyThuVien.Entity.Review;
import com.example.QuanLyThuVien.Service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/by-book")
    public List<ReviewDto> getReviewsByBookId(@RequestParam Integer bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    @PostMapping
    public String createReview(@RequestBody ReviewDto reviewDTO) {
        return reviewService.createReview(reviewDTO);
    }

}
