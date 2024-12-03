package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("""
        SELECT r FROM Review r 
        WHERE r.borrowRequestId.bookID.id = :bookId
        """)
    List<Review> findByBookId(Integer bookId);
}