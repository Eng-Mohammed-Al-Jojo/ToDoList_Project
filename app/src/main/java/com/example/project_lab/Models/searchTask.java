package com.example.project_lab.Models;

public class searchTask {
    private String ItemListTitle;
    private String TaskTitle;
    private int ItemListId;
    private int TaskId;

    public searchTask(String itemListTitle, String taskTitle, int itemListId, int taskId) {
        ItemListTitle = itemListTitle;
        TaskTitle = taskTitle;
        ItemListId = itemListId;
        TaskId = taskId;
    }

    public String getItemListTitle() {
        return ItemListTitle;
    }

    public void setItemListTitle(String itemListTitle) {
        ItemListTitle = itemListTitle;
    }

    public String getTaskTitle() {
        return TaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        TaskTitle = taskTitle;
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
