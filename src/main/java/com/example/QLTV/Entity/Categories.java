package com.example.QLTV.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Categories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "categoryID")
    private int categoryId;
    @Column(name = "name")
    private String name;
    @Version
    @Column(name = "version")
    private int version;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
