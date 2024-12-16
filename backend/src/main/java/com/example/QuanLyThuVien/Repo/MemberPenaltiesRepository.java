package com.example.QuanLyThuVien.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyThuVien.Entity.Fine;
import com.example.QuanLyThuVien.Entity.Member;
import com.example.QuanLyThuVien.Entity.MemberPenalties;

public interface MemberPenaltiesRepository  extends JpaRepository<MemberPenalties, Integer>  {
	 List<MemberPenalties> findByMember(Member member);

}
