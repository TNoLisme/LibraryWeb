package com.example.QLTV.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Members {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "memberID")
    private int memberId;
    @Basic
    @Column(name = "fullName")
    private String fullName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "role")
    private String role;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public Object getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Members members = (Members) o;
        return memberId == members.memberId && Objects.equals(fullName, members.fullName) && Objects.equals(email, members.email) && Objects.equals(password, members.password) && Objects.equals(role, members.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, fullName, email, password, role);
    }
}
