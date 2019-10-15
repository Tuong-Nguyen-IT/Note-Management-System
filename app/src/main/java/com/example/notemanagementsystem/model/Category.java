package com.example.notemanagementsystem.model;

public class Category {
    private String name;
    private String createdDate;

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}