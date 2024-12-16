package com.example.QuanLyThuVien.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fines")  
public class Fine {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fineID")  // Tên cột trong bảng
    private Integer fineID;

	 @Column(name = "fine_reason", nullable = false, length = 255)
	    private String fineReason;

	    @Column(name = "fine_amount", nullable = false, precision = 10, scale = 2)
	    private BigDecimal fineAmount;  // Đảm bảo tên cột trùng khớp với bảng

    public Fine() {
    }

  

    public Fine(Integer fineID, String fineReason, BigDecimal fineAmount) {
		super();
		this.fineID = fineID;
		this.fineReason = fineReason;
		this.fineAmount = fineAmount;
	}



	public Integer getFineID() {
        return fineID;
    }

    public void setFineID(Integer fineID) {
        this.fineID = fineID;
    }

    public String getFineReason() {
        return fineReason;
    }

    public void setFineReason(String fineReason) {
        this.fineReason = fineReason;
    }

	public BigDecimal getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}

}
