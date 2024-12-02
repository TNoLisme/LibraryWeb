package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.Entity.Category;
import com.example.QuanLyThuVien.Repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {
    @Autowired
    private CategoryRepository categoriesRepository;
    public Category saveCategory(Category category) {

            return categoriesRepository.save(category);

    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
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
}
