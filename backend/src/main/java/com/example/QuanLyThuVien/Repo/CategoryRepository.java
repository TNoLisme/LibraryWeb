package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.DTO.CategoryDTO;
import com.example.QuanLyThuVien.Entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	 @Query("SELECT new com.example.QuanLyThuVien.DTO.CategoryDTO(c.id, c.name) FROM Category c")
	    public List<CategoryDTO> getAllCategories();
}