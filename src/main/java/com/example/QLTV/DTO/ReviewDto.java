package com.example.QuanLyThuVien.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * DTO for {@link com.example.QuanLyThuVien.Entity.Review}
 */
@Getter
@Setter
@AllArgsConstructor
public class ReviewDto {
    private Integer id;
    private Integer bookID;
    private Integer memberID;
    private Integer rating;
    private String comment;
    private Instant reviewDate;
}
