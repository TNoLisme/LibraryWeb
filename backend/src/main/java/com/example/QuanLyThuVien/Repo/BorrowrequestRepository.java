package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.Entity.Borrowrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BorrowrequestRepository extends JpaRepository<Borrowrequest, Integer> {
    @Query("""
        SELECT b FROM Borrowrequest b 
        WHERE b.memberID.id = :memberId 
        AND b.id NOT IN (SELECT r.borrowRequestId.id FROM Review r)
        """)
    List<Borrowrequest> findBorrowRequestsNotInReviewsByMemberId(Integer memberId);
}