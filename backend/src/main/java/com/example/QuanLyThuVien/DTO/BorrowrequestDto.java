package com.example.QuanLyThuVien.DTO;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.example.QuanLyThuVien.Entity.Borrowrequest}
 */
@Value
@Data
public class BorrowrequestDto implements Serializable {
    Integer id;
    Integer bookID;
    Integer memID;
    LocalDate borrowDate;
    LocalDate returnDate;
    String status;
}