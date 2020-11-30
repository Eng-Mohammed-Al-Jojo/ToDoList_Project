package com.example.project_lab.Models;

import java.time.LocalDateTime;

public class Task {
    String Title;
    LocalDateTime CeateDate;
    String Description;
    boolean isChecked;

    public Task(String title, LocalDateTime ceateDate, String description, boolean isChecked) {
        Title = title;
        CeateDate = ceateDate;
        Description = description;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public LocalDateTime getCeateDate() {
        return CeateDate;
    }

    public void setCeateDate(LocalDateTime ceateDate) {
        CeateDate = ceateDate;
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
