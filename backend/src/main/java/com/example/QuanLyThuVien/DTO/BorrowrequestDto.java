package com.example.QuanLyThuVien.DTO;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * DTO for {@link com.example.QuanLyThuVien.Entity.Borrowrequest}
 */
@Value
@Data
public class BorrowrequestDto implements Serializable {
	 Integer id;
	    Integer bookID;
	    Integer memID;
	    Date borrowDate;  
	    Date returnDate;
	    String status;
	    String bookTitle; 
	    String memberFullName;
		public BorrowrequestDto(Integer id, Integer bookID, Integer memID, Date borrowDate, Date returnDate,
				String status, String bookTitle, String memberFullName) {
			super();
			this.id = id;
			this.bookID = bookID;
			this.memID = memID;
			this.borrowDate = borrowDate;
			this.returnDate = returnDate;
			this.status = status;
			this.bookTitle = bookTitle;
			this.memberFullName = memberFullName;
		} 
		
}