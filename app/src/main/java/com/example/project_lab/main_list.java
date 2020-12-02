package com.example.project_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Utils.staticData;

import java.util.ArrayList;

import static com.example.project_lab.Utils.staticData.list;

public class main_list extends AppCompatActivity {

    RecyclerView listRecycler;
    public listRecyclerAdapter listRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        listRecycler = findViewById(R.id.listRecycler);
        listRecycler.setLayoutManager(new LinearLayoutManager(this));

        listRecyclerAdapter = new listRecyclerAdapter(main_list.this, staticData.list);
        listRecycler.setAdapter(listRecyclerAdapter);

        EditText et_create_list = (EditText) findViewById(R.id.et_create_list);
        ImageButton btn_new_list = (ImageButton) findViewById(R.id.btn_new_list);
        btn_new_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = et_create_list.getText().toString().trim();
                if (titleText.isEmpty()) {
                    et_create_list.setError("this can not be empty");
                }else{
                    list.add(new ItemList(titleText,new ArrayList<Task>()));
                    et_create_list.getText().clear();
                    listRecyclerAdapter.notifyDataSetChanged();
                    //et_create_task.clearFocus();
                    Toast.makeText(main_list.this, "added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listRecyclerAdapter.notifyDataSetChanged();
    }
}