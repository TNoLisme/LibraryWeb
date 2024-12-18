package com.example.QuanLyThuVien.Controller;


import com.example.QuanLyThuVien.DTO.CategoryDTO;
import com.example.QuanLyThuVien.Entity.Category;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;
   
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoriesService.saveCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Optional<Category> category = categoriesService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoriesService.updateCategory(id, categoryDetails);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        // Kiểm tra xem thể loại có còn sách tham chiếu không
        boolean hasBooks = categoriesService.hasBooksInCategory(id);
        
        if (hasBooks) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Không thể xóa thể loại này vì vẫn còn sách thuộc thể loại này.");
        }

        // Nếu không có sách, xóa thể loại
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


}