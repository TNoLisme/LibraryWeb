package com.example.QuanLyThuVien.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "borrowrequests")
public class Borrowrequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestID", nullable = false)
    private Integer id;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bookID")
    private Book bookID;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "memberID")
    private Member memberID;

    @Column(name = "borrowDate", nullable = false)
    private Date borrowDate;


    @Column(name = "returnDate")
    private Date returnDate;
    @Lob
    @Column(name = "status")
    private String status;
   
    

	public Borrowrequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Borrowrequest(Integer id, Book bookID, Member memberID, Date borrowDate, Date returnDate,
			String status) {
		super();
		this.id = id;
		this.bookID = bookID;
		this.memberID = memberID;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.status = status;
	}

}