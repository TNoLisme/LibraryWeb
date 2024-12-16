package com.example.QuanLyThuVien.Entity;

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
    @Column(name = "fineID")
    private Integer fineID;

    @Column(name = "fineReason", nullable = false)
    private String fineReason;

    @Column(name = "fineAmount", nullable = false)
    private Double fineAmount;

    // Getters and Setters
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

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    // Constructor
    public Fine() {}

    public Fine(String fineReason, Double fineAmount) {
        this.fineReason = fineReason;
        this.fineAmount = fineAmount;
    }
}