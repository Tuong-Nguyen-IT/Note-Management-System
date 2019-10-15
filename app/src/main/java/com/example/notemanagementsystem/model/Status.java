package com.example.notemanagementsystem.model;

public class Status {
    private String name;
    private String date;
    public void Status(String name, String date){
        this.name = name;
        this.date = date;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
}
