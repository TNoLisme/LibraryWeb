package com.example.QuanLyThuVien.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyDetailsDto {

	 private String fullName;
	    private String fineReason;
	    private Double fineAmount;
	    private LocalDate penaltyDate;
	    private Boolean paidStatus;

	   
	   


}
