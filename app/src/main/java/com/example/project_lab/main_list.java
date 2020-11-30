package com.example.project_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project_lab.Adapters.listRecyclerApater;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;

import java.util.ArrayList;

public class main_list extends AppCompatActivity {

    RecyclerView listRecycler;
    listRecyclerApater listRecyclerApater;
    ArrayList<ItemList> list = new ArrayList<ItemList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        list.add(new ItemList("Personal",new ArrayList<Task>()));
        list.add(new ItemList("Home",new ArrayList<Task>()));
        list.add(new ItemList("Sport",new ArrayList<Task>()));
        list.add(new ItemList("Jop",new ArrayList<Task>()));
        list.add(new ItemList("Meeting",new ArrayList<Task>()));

        listRecycler = findViewById(R.id.listRecycler);
        listRecycler.setLayoutManager(new LinearLayoutManager(this));

        listRecyclerApater = new listRecyclerApater(main_list.this, list);
        listRecycler.setAdapter(listRecyclerApater);
    }



}