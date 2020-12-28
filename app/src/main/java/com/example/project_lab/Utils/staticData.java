package com.example.project_lab.Utils;

import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Models.searchTask;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class staticData {

    public static ArrayList<ItemList> list = new ArrayList<ItemList>();

    public static ArrayList<searchTask> searchTask(String searchText) {
        /*
        ArrayList<searchTask> result = new ArrayList<searchTask>();
        for (int i = 0; i < list.size(); i++) {
            ItemList listItem = list.get(i);
            for (int j = 0; j < listItem.getTasks().size(); j++) {
                Task task = list.get(i).getTasks().get(j);
                if (task.getTitle().toLowerCase().contains(searchText.toLowerCase())){
                    result.add(new searchTask(listItem.getId(),j));
                }
            }
        }*/
        return null;
    }


}
