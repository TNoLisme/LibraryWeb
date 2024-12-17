package com.example.QuanLyThuVien.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany
    @JoinTable(
        name = "bookcategory", 
        joinColumns = @JoinColumn(name = "bookid"),  
        inverseJoinColumns = @JoinColumn(name = "categoryid") 
    )
    private List<Category> categories; 

    // Các getter và setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

	public Book(Integer id, String title, String author, Integer publishYear, Integer quantity,
			List<Category> categories) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publishYear = publishYear;
		this.quantity = quantity;
		this.categories = categories;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
