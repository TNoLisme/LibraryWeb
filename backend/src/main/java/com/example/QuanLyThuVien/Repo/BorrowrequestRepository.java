package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.DTO.BorrowrequestDto;
import com.example.QuanLyThuVien.Entity.Borrowrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BorrowrequestRepository extends JpaRepository<Borrowrequest, Integer> {
	@Query("""
		    SELECT new com.example.QuanLyThuVien.DTO.BorrowrequestDto(
		        b.id, 
		        b.bookID.id, 
		        b.bookID.title, 
		        b.bookID.author
		    ) 
		    FROM Borrowrequest b 
		    WHERE b.memberID.id = :memberId 
		    AND b.id NOT IN (SELECT r.borrowRequestId.id FROM Review r)
		""")
		List<BorrowrequestDto> findBorrowRequestsNotInReviewsByMemberId(Integer memberId);

    
    @Query("""
    	    SELECT new com.example.QuanLyThuVien.DTO.BorrowrequestDto(
    	        b.id,
    	        b.bookID.id,
    	        b.memberID.id,
    	        b.borrowDate,
    	        b.returnDate,
    	        b.status,
    	        b.bookID.title,  
    	        b.memberID.fullName  
    	    )
    	    FROM Borrowrequest b
    	""")
    	List<BorrowrequestDto> findAllBorrowRequestsAsDto();


}