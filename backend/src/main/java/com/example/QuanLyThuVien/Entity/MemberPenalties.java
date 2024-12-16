package com.example.QuanLyThuVien.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "memberpenalties")
public class MemberPenalties {

	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer penaltyRecordID;  // ID của bản ghi phạt
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", referencedColumnName = "memberID")
    private Member member;  // Liên kết tới entity Member
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fineID", referencedColumnName = "fineID")
    private Fine fine;  // Liên kết tới entity Fine
    
    private LocalDate penaltyDate;  // Ngày phạt
    
    @Enumerated(EnumType.STRING)
    private PaidStatus paidStatus;  // Trạng thái thanh toán

    
   
    public MemberPenalties() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MemberPenalties(Integer penaltyRecordID, Member member, Fine fine, LocalDate penaltyDate,
			PaidStatus paidStatus) {
		super();
		this.penaltyRecordID = penaltyRecordID;
		this.member = member;
		this.fine = fine;
		this.penaltyDate = penaltyDate;
		this.paidStatus = paidStatus;
	}



	public Integer getPenaltyRecordID() {
		return penaltyRecordID;
	}



	public void setPenaltyRecordID(Integer penaltyRecordID) {
		this.penaltyRecordID = penaltyRecordID;
	}



	public Member getMember() {
		return member;
	}



	public void setMember(Member member) {
		this.member = member;
	}



	public Fine getFine() {
		return fine;
	}



	public void setFine(Fine fine) {
		this.fine = fine;
	}



	public LocalDate getPenaltyDate() {
		return penaltyDate;
	}



	public void setPenaltyDate(LocalDate penaltyDate) {
		this.penaltyDate = penaltyDate;
	}



	public PaidStatus getPaidStatus() {
		return paidStatus;
	}



	public void setPaidStatus(PaidStatus paidStatus) {
		this.paidStatus = paidStatus;
	}



	public enum PaidStatus {
        PAID, UNPAID
    }
}