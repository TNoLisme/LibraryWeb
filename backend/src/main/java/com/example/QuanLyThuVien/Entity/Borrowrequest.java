package com.example.QuanLyThuVien.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

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
    private LocalDate borrowDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Lob
    @Column(name = "status")
    private String status;

}