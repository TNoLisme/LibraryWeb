package com.example.QLTV.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Borrowrequests {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "requestID")
    private int requestId;
    @Basic
    @Column(name = "memberID")
    private Integer memberId;
    @Basic
    @Column(name = "bookID")
    private Integer bookId;
    @Basic
    @Column(name = "borrowDate")
    private Date borrowDate;
    @Basic
    @Column(name = "returnDate")
    private Date returnDate;
    @Basic
    @Column(name = "status")
    private String status;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowrequests that = (Borrowrequests) o;
        return requestId == that.requestId && Objects.equals(memberId, that.memberId) && Objects.equals(bookId, that.bookId) && Objects.equals(borrowDate, that.borrowDate) && Objects.equals(returnDate, that.returnDate) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, memberId, bookId, borrowDate, returnDate, status);
    }
}
