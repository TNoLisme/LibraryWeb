package com.example.QuanLyThuVien.Entity;

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
import java.time.LocalDate;

@Entity
@Table(name = "memberpenalties")
public class MemberPenalties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_recordid")
    private Integer penaltyRecordID;  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", referencedColumnName = "memberID")
    private Member member;  // Liên kết tới entity Member

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fineID", referencedColumnName = "fineID")
    private Fine fine;  // Liên kết với bảng fines thông qua fineID


    @Column(name = "penalty_date", nullable = false)
    private LocalDate penaltyDate;  // Ngày phạt

    @Enumerated(EnumType.STRING)
    @Column(name = "paid_status", columnDefinition = "ENUM('paid', 'unpaid') DEFAULT 'unpaid'")
    private PaidStatus paidStatus;


    public MemberPenalties() {
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
        paid,
        unpaid;
    }

}
