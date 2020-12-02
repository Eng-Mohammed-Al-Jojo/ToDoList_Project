package com.example.project_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Utils.staticData;

public class Details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

//        Activity t =  (Activity)getApplicationContext();

        int listId = getIntent().getIntExtra("listId",-1);
        int taskPosition = getIntent().getIntExtra("taskPosition",-1);

        ItemList listItem = staticData.list.get(listId-1);
        Task task = listItem.getTasks().get(taskPosition);

        TextView tv_detailsTitle = (TextView) findViewById(R.id.tv_detailsTitle);
        TextView tv_detailsLabel = (TextView) findViewById(R.id.tv_detailsLabel);
        TextView tv_detailsDate = (TextView) findViewById(R.id.tv_detailsDate);
        TextView tv_detailsDescription = (TextView) findViewById(R.id.tv_detailsDescription);

        tv_detailsTitle.setText(task.getTitle());
        tv_detailsLabel.setText(listItem.getTitle());
        tv_detailsDate.setText(task.getDate());
        tv_detailsDescription.setText(task.getDescription());

        Button btn_delete_task = (Button) findViewById(R.id.btn_delete_task);
        btn_delete_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listItem.getTasks().remove(task);
                Details_Activity.this.finish();
            }
        });

    }
}