package com.example.project_lab.Models;

import java.util.ArrayList;

public class ItemList {
    String Title;
    ArrayList<Task> Tasks;

    public ItemList(String title, ArrayList<Task> tasks) {
        Title = title;
        Tasks = tasks;
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
