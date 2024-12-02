package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    Admin findByEmail(String email);
}