package com.example.project_lab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText login_email,login_password;
    Button login_btn;
    TextView To_register ,To_tasksList;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        To_register = (TextView) findViewById(R.id.To_register);
        login_btn=(Button)findViewById(R.id.login_btn);
        login_email=(EditText)findViewById(R.id.login_email);
        login_password=(EditText)findViewById(R.id.login_password);
        //To_tasksList = (TextView) findViewById(R.id.To_tasksList);

        To_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

   /*     To_tasksList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, main_list.class);
                startActivity(intent);
            }
        });
*/

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();
                doLogin(email, password);
            }
        });
    }

    private void doLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Successfully Done!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Login.this,main_list.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent=new Intent(Login.this,main_list.class);
            startActivity(intent);
        }
    }

}