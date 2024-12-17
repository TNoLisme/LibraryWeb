package com.example.QuanLyThuVien.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.QuanLyThuVien.DTO.PenaltyDetailsDto;
import com.example.QuanLyThuVien.Entity.Fine;
import com.example.QuanLyThuVien.Entity.Member;
import com.example.QuanLyThuVien.Entity.MemberPenalties;

public interface MemberPenaltiesRepository  extends JpaRepository<MemberPenalties, Integer>  {
	 List<MemberPenalties> findByMember(Member member);
	 
	 @Query("""
		        SELECT mp
		        FROM MemberPenalties mp
		        JOIN FETCH mp.member m
		        JOIN FETCH mp.fine f
		    """)
		    List<MemberPenalties> findAllWithDetails();
	 @Query("""
			    SELECT mp
			    FROM MemberPenalties mp
			    JOIN FETCH mp.member m
			    JOIN FETCH mp.fine f
			    WHERE m.id = :memberId
			""")
			List<MemberPenalties> findAllByMemberIdWithDetails(int memberId);
}
