package com.example.QuanLyThuVien.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPenaltiesDTO {
	 private int penaltyRecordID;
	    private String paidStatus;
}
