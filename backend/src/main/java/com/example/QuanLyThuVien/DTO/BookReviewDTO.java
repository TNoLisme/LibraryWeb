package com.example.QuanLyThuVien.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewDTO {
    BorrowrequestDto a;
    String status;
}
