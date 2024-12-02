package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByMemberID_Id(Integer memberId);
}