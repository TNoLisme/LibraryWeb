package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.DTO.ReviewDto;
import com.example.QuanLyThuVien.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	@Query("""
		    SELECT new com.example.QuanLyThuVien.DTO.ReviewDto(
		        r.borrowRequestId.id, 
		        r.memberID.id, 
		        r.memberID.fullName,  
		        r.rating, 
		        r.comment, 
		        r.reviewDate
		    )
		    FROM Review r
		    WHERE r.borrowRequestId.bookID.id = :bookId
		""")
		List<ReviewDto> findByBookId(Integer bookId);


}