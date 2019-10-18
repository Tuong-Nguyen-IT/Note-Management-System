package com.example.notemanagementsystem.model;

public class Note {
    private String name;
    private String category_name;
    private String priority_name;
    private String status_name;
    private String plan_date;
    private String createdDate;

    public Note(String name,String category_name,String priority_name,String status_name,String plan_date, String createdDate) {
        this.name = name;
        this.category_name = category_name;
        this.priority_name = priority_name;
        this.status_name = status_name;
        this.plan_date = plan_date;
        this.createdDate = createdDate;
    }

    public Note(String name,String category_name,String priority_name,String status_name,String plan_date) {
        this.name = name;
        this.category_name = category_name;
        this.priority_name = priority_name;
        this.status_name = status_name;
        this.plan_date = plan_date;

    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPriority_name() {
        return priority_name;
    }

    public void setPriority_name(String priority_name) {
        this.priority_name = priority_name;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
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
