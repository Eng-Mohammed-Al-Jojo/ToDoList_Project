package com.example.project_lab.Models;

import java.time.LocalDateTime;

public class Task {
    private String Title;
    private String Date;
    private String Description;
    boolean isChecked;

    public Task(String title, String date, String description, boolean isChecked) {
        Title = title;
        Date = date;
        Description = description;
        this.isChecked = isChecked;
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
}
