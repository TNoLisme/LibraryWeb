package com.example.QuanLyThuVien.Entity;
import jakarta.persistence.*;
@Entity
@Table(name = "bookcategory")
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookID", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = false)
    private Category category;

    public BookCategory(Book book, Category category) {
        this.book = book;
        this.category = category;
    }
  
	public BookCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookCategory(Long id, Book book, Category category) {
		super();
		this.id = id;
		this.book = book;
		this.category = category;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}