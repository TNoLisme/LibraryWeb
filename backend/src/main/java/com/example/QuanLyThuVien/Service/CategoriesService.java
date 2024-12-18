package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.CategoryDTO;
import com.example.QuanLyThuVien.Entity.Category;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {
    @Autowired
    private CategoryRepository categoriesRepository;
    @Autowired
    private BookRepository booksRepository;
    public Category saveCategory(Category category) {

            return categoriesRepository.save(category);

    }
    public Optional<Category> getCategoryById(Integer id) {
        return categoriesRepository.findById(id);
    }


    // Get all categories
    public List<CategoryDTO> getAllCategories() {
        return categoriesRepository.getAllCategories();
    }

    // Get category by id
    public Optional<Category> getCategoryById(int id) {
        return categoriesRepository.findById(id);
    }

    // Update category
    public Category updateCategory(int id, Category categoryDetails) {
        if (categoriesRepository.existsById(id)) {
            categoryDetails.setId(id);
            return categoriesRepository.save(categoryDetails);
        }
        return null;
    }

    // Delete category
    public void deleteCategory(int id) {
        categoriesRepository.deleteById(id);
    }
    // Delete category
    public boolean hasBooksInCategory(int categoryId) {
        // Kiểm tra xem có sách nào còn tham chiếu tới thể loại này không
        return booksRepository.existsByCategoriesId(categoryId);
    }
}
