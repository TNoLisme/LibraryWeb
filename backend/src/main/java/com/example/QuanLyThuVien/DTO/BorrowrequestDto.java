package com.example.QuanLyThuVien.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.example.QuanLyThuVien.Entity.Borrowrequest}
 */
@Data
@AllArgsConstructor  // Constructor đầy đủ cho tất cả các trường
@NoArgsConstructor  // Constructor không tham số để Hibernate có thể tạo đối tượng rỗng
public class BorrowrequestDto implements Serializable {
    private Integer id;
    private Integer bookID;
    private Integer memID;
    private Date borrowDate;  
    private Date returnDate;
    private String status;
    private String bookTitle; 
    private String memberFullName;

    // Constructor thiếu chỉ có các trường bạn cần từ query
    public BorrowrequestDto(Integer id, Integer bookID, String bookTitle, String bookAuthor) {
        this.id = id;
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.memberFullName = bookAuthor;  // Book author sẽ được gán cho full name
    }
}
