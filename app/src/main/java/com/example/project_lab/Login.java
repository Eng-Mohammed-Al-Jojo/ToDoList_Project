package com.example.project_lab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    TextView To_register ,To_tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView To_register = (TextView) findViewById(R.id.To_register);

        To_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        TextView To_tasksList = (TextView) findViewById(R.id.To_tasksList);

        To_tasksList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, main_list.class);
                startActivity(intent);
            }
        });


    }
}