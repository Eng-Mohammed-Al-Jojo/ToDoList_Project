package com.example.project_lab.Models;

import java.time.LocalDateTime;

public class Task {

    String id;
    private String Title;
    private String Date;
    private String Description;
    boolean isChecked;
    String listId;

    public Task(){}

    public Task(String title, String date, String description, boolean isChecked, String listId) {
        Title = title;
        Date = date;
        Description = description;
        this.isChecked = isChecked;
        this.listId = listId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
