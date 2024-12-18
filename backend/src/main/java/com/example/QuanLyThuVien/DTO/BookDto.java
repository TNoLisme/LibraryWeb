package com.example.QuanLyThuVien.DTO;

import com.example.QuanLyThuVien.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link com.example.QuanLyThuVien.Entity.Book}
 */
@Data

public class BookDto implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private Integer publishYear;
    private Integer quantity;
    private List<CategoryDTO> categoryDtos;  
    private List<Integer> categoryIds;
    // Constructor chỉ nhận title và author
    public BookDto(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    public List<CategoryDTO> getCategoryDtos() {
        return categoryDtos;
    }

	public BookDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookDto(Integer id, String title, String author, Integer publishYear, Integer quantity,
			List<CategoryDTO> categoryDtos) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishYear = publishYear;
		this.quantity = quantity;
		this.categoryDtos = categoryDtos;
	}

}
