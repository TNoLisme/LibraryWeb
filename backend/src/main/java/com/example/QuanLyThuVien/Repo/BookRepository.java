package com.example.QuanLyThuVien.Repo;

import com.example.QuanLyThuVien.DTO.BookDto;
import com.example.QuanLyThuVien.Entity.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("SELECT new com.example.QuanLyThuVien.DTO.BookDto(b.id, b.title, b.author) FROM Book b")
	List<BookDto> getAllBooks();

}

