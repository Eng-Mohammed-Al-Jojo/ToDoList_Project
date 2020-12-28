package com.example.project_lab.Models;

import java.util.ArrayList;

public class ItemList {

    String Id;
    String Title;
    int numOfTasks;

    public ItemList(){}

    public ItemList(String title, int numOfTasks) {
        this.Title = title;
        this.numOfTasks = numOfTasks;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }

    public void setNumOfTasks(int numOfTasks) {
        this.numOfTasks = numOfTasks;
    }
}
