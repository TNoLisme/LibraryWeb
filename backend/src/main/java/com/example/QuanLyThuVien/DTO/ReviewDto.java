package com.example.QuanLyThuVien.DTO;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class ReviewDto {
    private Integer borrowRequestId;
    private Integer memberId;
    private String memberFullName;  // Thêm trường này để chứa tên người đánh giá
    private Integer rating;
    private String comment;
    private Date reviewDate;

    // Constructor cập nhật
    public ReviewDto(Integer borrowRequestId, Integer memberId, String memberFullName, Integer rating, String comment, Date reviewDate) {
        this.borrowRequestId = borrowRequestId;
        this.memberId = memberId;
        this.memberFullName = memberFullName;  // Khởi tạo tên người đánh giá
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Các getter và setter
    public String getMemberFullName() {
        return memberFullName;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    // Các getter/setter khác...
}

