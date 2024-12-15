package com.example.QuanLyThuVien.DTO;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class ReviewDto {
    private Integer borrowRequestId;
    private Integer memberId;
    private Integer rating;
    private String comment;
    private Date reviewDate;
    
}
