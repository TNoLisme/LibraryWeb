package com.example.QLTV.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Books {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bookID")
    private int bookId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "author")
    private String author;
    @Basic
    @Column(name = "categoryID")
    private Integer categoryId;
    @Basic
    @Column(name = "publish_year")
    private Integer publishYear;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return bookId == books.bookId && Objects.equals(title, books.title) && Objects.equals(author, books.author) && Objects.equals(categoryId, books.categoryId) && Objects.equals(publishYear, books.publishYear) && Objects.equals(quantity, books.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, categoryId, publishYear, quantity);
    }
}
