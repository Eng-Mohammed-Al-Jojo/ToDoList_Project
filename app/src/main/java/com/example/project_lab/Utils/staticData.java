package com.example.project_lab.Utils;

import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class staticData {

    public static ArrayList<ItemList> list = new ArrayList<ItemList>();

    public static void fillData() {
        ArrayList<Task> personal = new ArrayList<Task>();
        personal.add(new Task("personal Test","2020/11/1 07:30:00","this is first Task to do",false));
        personal.add(new Task("personal Test2","2020/11/2 07:30:00","this is second Task to do",true));
        personal.add(new Task("personal Test3","2020/11/3 07:30:00","this is third Task to do",false));
        personal.add(new Task("personal Test4","2020/11/4 07:30:00","this is fourth Task to do",true));
        list.add(new ItemList("Personal",personal));

        ArrayList<Task> home = new ArrayList<Task>();
        home.add(new Task("home Test","2020/11/5 07:30:00","this is first Task to do",true));
        home.add(new Task("home Test2","2020/11/6 07:30:00","this is second Task to do",false));
        home.add(new Task("home Test3","2020/11/7 07:30:00","this is third Task to do",true));
        home.add(new Task("home Test4","2020/11/8 07:30:00","this is fourth Task to do",false));
        list.add(new ItemList("Home",home));

        list.add(new ItemList("Sport",new ArrayList<Task>()));
        list.add(new ItemList("Jop",new ArrayList<Task>()));
        list.add(new ItemList("Meeting",new ArrayList<Task>()));
    }

}
