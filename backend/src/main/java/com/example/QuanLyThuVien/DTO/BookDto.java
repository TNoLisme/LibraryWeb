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
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private Integer publishYear;
    private Integer quantity;
    private List<CategoryDTO> categoryDtos;  
    // Constructor chỉ nhận title và author
    public BookDto(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    public List<CategoryDTO> getCategoryDtos() {
        return categoryDtos;
    }

}
