package com.example.QuanLyThuVien.Entity;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberID", nullable = false) 
    private Integer id;

    @Column(name = "fullName", nullable = false) 
    private String fullName;

    @Column(name = "email", nullable = false, unique = true) 
    private String email;

    @Column(name = "password", nullable = false) 
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public void setMemberID(int i) {
		// TODO Auto-generated method stub
		
	}
}