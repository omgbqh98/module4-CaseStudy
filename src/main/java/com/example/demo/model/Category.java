package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_category;
    private String icon;
    private String name;

    public Category(Long id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    @Transient
    private MultipartFile iconCategoryFile;

    public Category() {
    }

    public MultipartFile getIconCategoryFile() {
        return iconCategoryFile;
    }

    public void setIconCategoryFile(MultipartFile iconCategoryFile) {
        this.iconCategoryFile = iconCategoryFile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId_category() {
        return id_category;
    }

    public void setId_category(Long id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
