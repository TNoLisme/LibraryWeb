package com.example.QLTV.Service;

import com.example.QLTV.Entity.Categories;
import com.example.QLTV.Repository.CategoriesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    // Create or update a category
    @Transactional
    public Categories saveCategory(Categories category) {
      try{
          return categoriesRepository.save(category);
      }catch (Exception ex){
          System.out.println("a" + ex.getStackTrace());
          return null;
      }
    }

    // Get all categories
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    // Get category by id
    public Optional<Categories> getCategoryById(int id) {
        return categoriesRepository.findById(id);
    }

    // Update category
    public Categories updateCategory(int id, Categories categoryDetails) {
        if (categoriesRepository.existsById(id)) {
            categoryDetails.setCategoryId(id);
            return categoriesRepository.save(categoryDetails);
        }
        return null;
    }

    // Delete category
    public void deleteCategory(int id) {
        categoriesRepository.deleteById(id);
    }
}
