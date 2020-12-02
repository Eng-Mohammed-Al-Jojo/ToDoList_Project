package com.example.project_lab.Models;

import java.util.ArrayList;

public class ItemList {
    int Id;
    String Title;
    ArrayList<Task> Tasks;

    public ItemList(int id, String title, ArrayList<Task> tasks) {
        Id = id;
        Title = title;
        Tasks = tasks;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Task> getTasks() {
        return Tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        Tasks = tasks;
    }
}
