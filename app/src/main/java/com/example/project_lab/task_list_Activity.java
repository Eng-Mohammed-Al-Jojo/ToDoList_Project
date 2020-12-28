package com.example.project_lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lab.Adapters.taskRecyclerAdapter;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Utils.FirebaseConnection;
import com.example.project_lab.Utils.staticData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.project_lab.Utils.staticData.list;


public class task_list_Activity extends AppCompatActivity {

    RecyclerView taskRecycler;
    taskRecyclerAdapter taskRecyclerAdapter;
    ArrayList<Task> tasks;
    String listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_);

        listId = getIntent().getStringExtra("clickedId");
        taskRecycler = findViewById(R.id.taskRecycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseConnection firebaseConnection = new FirebaseConnection();
        this.tasks = firebaseConnection.tasks;

        taskRecyclerAdapter = new taskRecyclerAdapter(task_list_Activity.this, tasks);
        taskRecycler.setAdapter(taskRecyclerAdapter);

        firebaseConnection.getTasks(taskRecyclerAdapter, listId);

        Activity activity = this;

        /*Create new Task code */

        EditText et_create_task = (EditText) findViewById(R.id.et_create_task);
        ImageButton btn_new_task = (ImageButton) findViewById(R.id.btn_new_task);

        btn_new_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = et_create_task.getText().toString().trim();
                if (titleText.isEmpty()) {
                    et_create_task.setError("this can not be empty");
                } else {
                   // FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks/"+taskId).setValue(task);

                    Task task = new Task(titleText,null,"No Description",false,listId);
                    firebaseConnection.addTask(task,listId);
                    et_create_task.getText().clear();
                    HideKeyboard();
                    taskRecyclerAdapter.notifyDataSetChanged();
                    Toast.makeText(task_list_Activity.this, "added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        et_create_task.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String titleText = et_create_task.getText().toString().trim();
                    if (titleText.isEmpty()) {
                        et_create_task.setError("this can not be empty");
                    } else {
                        Task task = new Task(titleText,null,"No Description",false,listId);
                        firebaseConnection.addTask(task, listId);
                        et_create_task.getText().clear();
                        HideKeyboard();
                        Toast.makeText(task_list_Activity.this, "added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        /* Delete task Button */

        Button btn_delete_list = (Button) findViewById(R.id.btn_delete_list);
        btn_delete_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                new AlertDialog.Builder(activity)
                        .setTitle("Do you want to remove list?")
                        .setMessage("Are you sure about delete this list ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseDatabase.getInstance().getReference("users/"+uid+"/lists/"+listId).removeValue();
                                FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks").orderByChild("listId").equalTo(listId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                            Task task = dataSnapshot.getValue(Task.class);
                                            FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks/"+task.getId()).removeValue();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        taskRecyclerAdapter.notifyDataSetChanged();
    }

    protected void HideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }
}