package com.example.QuanLyThuVien.DTO;

import com.example.QuanLyThuVien.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.QuanLyThuVien.Entity.Book}
 */
@Value
@AllArgsConstructor
@Data

public class BookDto implements Serializable {
	  private Integer id;
	    private String title;
	    private String author;
	    private Integer publishYear;
	    private Integer quantity;
	    private List<Integer> categoryIds;
    
}