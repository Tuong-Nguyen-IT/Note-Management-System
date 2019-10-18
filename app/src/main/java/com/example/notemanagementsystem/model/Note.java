package com.example.notemanagementsystem.model;

public class Note {
    private String name;

    private String createdDate;

    public Note(String name) {
        this.name = name;
    }

    public Note(String name, String createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }

    public String getName() { return name; }

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
