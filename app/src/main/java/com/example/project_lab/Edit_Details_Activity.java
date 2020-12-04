package com.example.project_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Edit_Details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__details_);


        ImageButton  btn_back_to_details = (ImageButton) findViewById(R.id.btn_back_to_details);
        btn_back_to_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Edit_Details_Activity.this, Details_Activity.class);

                Edit_Details_Activity.this.finish();
            }
        });
    }
}