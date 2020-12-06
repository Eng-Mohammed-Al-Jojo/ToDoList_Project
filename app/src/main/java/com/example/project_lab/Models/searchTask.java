package com.example.project_lab.Models;

public class searchTask {
    private int ItemListId;
    private int TaskId;

    public searchTask(int itemListId, int taskId) {
        ItemListId = itemListId;
        TaskId = taskId;
    }

    public int getItemListId() {
        return ItemListId;
    }

    public void setItemListId(int itemListId) {
        ItemListId = itemListId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }
}