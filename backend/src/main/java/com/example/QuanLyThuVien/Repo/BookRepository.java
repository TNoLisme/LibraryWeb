package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.DTO.BookDto;
import com.example.QuanLyThuVien.Entity.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("SELECT b.id, b.title, b.author, b.publishYear, b.quantity, c.id, c.name " +
		       "FROM Book b LEFT JOIN b.categories c")
		List<Object[]> getAllBooksWithCategories();


		 boolean existsByCategoriesId(int categoryId);

		    // Đếm số sách thuộc thể loại với ID cho trước
		    long countByCategoriesId(int categoryId);
}

