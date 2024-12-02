package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}