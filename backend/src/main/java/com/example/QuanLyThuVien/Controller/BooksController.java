package com.example.QuanLyThuVien.Controller;


import com.example.QuanLyThuVien.DTO.BookDto;
import com.example.QuanLyThuVien.DTO.CategoryDTO;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.Category;
import com.example.QuanLyThuVien.Service.BooksService;
import com.example.QuanLyThuVien.Service.CategoriesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BooksService booksService;
    @Autowired
    private CategoriesService  categoryService;
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto book) {
        try {
           
            System.out.println("Attempting to create book with title: " + book.getTitle());

            if (book == null || book.getTitle() == null || book.getTitle().isEmpty()) {
                throw new RuntimeException("Book data is invalid: Missing title");
            }

            
            BookDto savedBook =  booksService.saveBook(book);

            // Log thông tin sách đã được lưu
            System.out.println("Book created successfully with ID: " + savedBook.getId());

            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.err.println("Error occurred while creating book: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.err.println("Unexpected error occurred while creating book: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return booksService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable int id) {
        Optional<BookDto> book = booksService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable int id, @RequestBody BookDto bookDetails) {
        // Kiểm tra nếu categoryIds không null và chuyển thành danh sách CategoryDTO
        if (bookDetails.getCategoryIds() != null) {
            List<CategoryDTO> categories = new ArrayList<>();
            for (Integer categoryId : bookDetails.getCategoryIds()) {
                // Lấy category từ service (Optional<Category>)
                Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);

                // Kiểm tra nếu category tồn tại
                if (categoryOptional.isPresent()) {
                    // Chuyển Category thành CategoryDTO
                    Category category = categoryOptional.get();
                    CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName());
                    categories.add(categoryDTO);
                } else {
                    // Xử lý nếu category không tồn tại (nếu cần log hoặc ném ngoại lệ)
                    System.err.println("Category with ID " + categoryId + " not found.");
                }
            }

            // Cập nhật danh sách categoryDtos trong bookDetails
            bookDetails.setCategoryDtos(categories);
        }

        // Thực hiện cập nhật sách
        BookDto updatedBook = booksService.updateBook(id, bookDetails);
        
        // Trả về kết quả
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        booksService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
