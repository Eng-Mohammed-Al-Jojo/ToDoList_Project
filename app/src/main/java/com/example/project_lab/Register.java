package com.example.project_lab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText RegisterEmail,RegisterPassword ,RegisterName;
    Button Register_btn;
    TextView To_login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user=mAuth.getCurrentUser();

        RegisterEmail = findViewById(R.id.RegisterEmail);
        RegisterPassword = findViewById(R.id.RegisterPassword);
        To_login = findViewById(R.id.To_login);
        Register_btn = findViewById(R.id.Register_btn);

        To_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegisterEmail.getText().toString();
                String password = RegisterPassword.getText().toString();
                doRegister(email, password);
            }
        });
    }

    private void doRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("NoError", "Good");
                            Toast.makeText(Register.this, "Successfully Done!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Register.this,main_list.class);
                             startActivity(intent);
                        } else {
                            Log.e("MyError", task.getException().getMessage());
                            Toast.makeText(Register.this, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent=new Intent(Register.this,main_list.class);
            startActivity(intent);
        }

    }
}