package com.example.QLTV.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Reviews {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reviewID")
    private int reviewId;
    @Basic
    @Column(name = "bookID")
    private Integer bookId;
    @Basic
    @Column(name = "memberID")
    private Integer memberId;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "reviewDate")
    private Timestamp reviewDate;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reviews reviews = (Reviews) o;
        return reviewId == reviews.reviewId && Objects.equals(bookId, reviews.bookId) && Objects.equals(memberId, reviews.memberId) && Objects.equals(rating, reviews.rating) && Objects.equals(comment, reviews.comment) && Objects.equals(reviewDate, reviews.reviewDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, bookId, memberId, rating, comment, reviewDate);
    }
}
