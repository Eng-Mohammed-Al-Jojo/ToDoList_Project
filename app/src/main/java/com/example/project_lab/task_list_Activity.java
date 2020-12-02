package com.example.project_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Adapters.taskRecyclerAdapter;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Utils.staticData;

import java.util.ArrayList;

import static com.example.project_lab.Utils.staticData.list;

public class task_list_Activity extends AppCompatActivity {

    RecyclerView taskRecycler;
    com.example.project_lab.Adapters.taskRecyclerAdapter taskRecyclerAdapter;
    ItemList listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_);

        int pos = getIntent().getIntExtra("clickedPosition",-1);
        listItem = staticData.list.get(pos);

        taskRecycler = findViewById(R.id.taskRecycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));

        taskRecyclerAdapter = new taskRecyclerAdapter(task_list_Activity.this, listItem);
        taskRecycler.setAdapter(taskRecyclerAdapter);

        EditText et_create_task = (EditText) findViewById(R.id.et_create_task);
        ImageButton btn_new_task = (ImageButton) findViewById(R.id.btn_new_task);
        btn_new_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = et_create_task.getText().toString().trim();
                if (titleText.isEmpty()) {
                    et_create_task.setError("this can not be empty");
                }else{
                    listItem.getTasks().add(new Task(titleText,"","",false));
                    et_create_task.getText().clear();
                    //et_create_task.clearFocus();
                    taskRecyclerAdapter.notifyDataSetChanged();
                    Toast.makeText(task_list_Activity.this, "added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn_delete_list = (Button) findViewById(R.id.btn_delete_list);
        btn_delete_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                staticData.list.remove(listItem);
                task_list_Activity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        taskRecyclerAdapter.notifyDataSetChanged();
    }
}