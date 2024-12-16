package com.example.QuanLyThuVien.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyThuVien.Entity.Fine;

public interface FineRepository extends JpaRepository<Fine, Integer> {
//	 Optional<Fine> findByFineReason(String fineReason);
}
