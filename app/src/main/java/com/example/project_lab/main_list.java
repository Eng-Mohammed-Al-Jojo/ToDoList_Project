package com.example.project_lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Adapters.searchAdapter;
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

public class main_list extends AppCompatActivity {

    Button Logout;
    ImageButton btn_searchTask,btn_new_list;
    RecyclerView listRecycler;
    EditText et_searchTask,et_create_list;
    public listRecyclerAdapter listRecyclerAdapter;
    public searchAdapter searchAdapter;
    ArrayList<ItemList> lists;
    ArrayList<Task> searchTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        Logout = findViewById(R.id.Logout);
        listRecycler = findViewById(R.id.listRecycler);
        listRecycler.setLayoutManager(new LinearLayoutManager(this));

        /* Getting From Firebase **/
        FirebaseConnection firebaseConnection = new FirebaseConnection();

        listRecyclerAdapter = new listRecyclerAdapter(main_list.this, firebaseConnection.lists);
        listRecycler.setAdapter(listRecyclerAdapter);

        firebaseConnection.getLists(listRecyclerAdapter);
        Activity activity = this;


        Logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             new AlertDialog.Builder(activity)
                    .setTitle("Do you want to Logout ?")
                        .setMessage("Are you sure about LogOut form this account ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(main_list.this, Login.class);
                    startActivity(intent);
                    finish();
                }})
                .setNegativeButton(android.R.string.no, null).show();
            }
        });


        /*  Create New Item list*/

         et_create_list =  findViewById(R.id.et_create_list);
         btn_new_list =  findViewById(R.id.btn_new_list);
        btn_new_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = et_create_list.getText().toString().trim();
                if (titleText.isEmpty()) {
                    et_create_list.setError("this can not be empty");
                } else {
                    ItemList itemList = new ItemList(titleText, 0);
                    firebaseConnection.addList(itemList);
                    et_create_list.getText().clear();
                    listRecyclerAdapter.notifyDataSetChanged();
                    HideKeyboard();
                    Toast.makeText(main_list.this, "added successfully", Toast.LENGTH_SHORT).show();
                }
            }


        });


        et_create_list.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String titleText = et_create_list.getText().toString().trim();
                    if (titleText.isEmpty()) {
                        et_create_list.setError("this can not be empty");
                    } else {
                        ItemList itemList = new ItemList(titleText, 0);
                        firebaseConnection.addList(itemList);
                        et_create_list.getText().clear();
                        listRecyclerAdapter.notifyDataSetChanged();
                        HideKeyboard();
                        Toast.makeText(main_list.this, "added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        /* Search */

        et_searchTask = findViewById(R.id.et_searchTask);
        btn_searchTask =  findViewById(R.id.btn_searchTask);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        btn_searchTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String searchText = et_searchTask.getText().toString().trim();
                if (searchText.isEmpty()) {
                    et_searchTask.setError("Con not be empty!");
                    listRecycler.setAdapter(listRecyclerAdapter);
                } else {

                    searchAdapter = new searchAdapter(main_list.this, searchTasks);
                    listRecycler.setAdapter(searchAdapter);

                    FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            searchTasks.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Task task = dataSnapshot.getValue(Task.class);
                                if(task.getTitle().toLowerCase().contains(searchText.toLowerCase())){
                                    searchTasks.add(task);
                                }
                            }
                            searchAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });



                    LinearLayout create_layout=findViewById(R.id.create_layout);
                    create_layout.setVisibility(View.GONE);

                    TextView tasksText= findViewById(R.id.tasksText);
                    tasksText.setText("Results :  ");
                }
                HideKeyboard();
            }
        });




        et_searchTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = et_searchTask.getText().toString().trim();
                if (searchText.isEmpty()) {
                    listRecycler.setAdapter(listRecyclerAdapter);
                } else {

                    searchAdapter = new searchAdapter(main_list.this, searchTasks);
                    listRecycler.setAdapter(searchAdapter);
                    FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            searchTasks.clear();
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Task task = dataSnapshot.getValue(Task.class);
                                if(task.getTitle().toLowerCase().contains(searchText.toLowerCase())){
                                    searchTasks.add(task);
                                }
                            }
                            searchAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                LinearLayout create_layout=findViewById(R.id.create_layout);
                create_layout.setVisibility(View.VISIBLE);

                TextView tasksText= findViewById(R.id.tasksText);
                tasksText.setText("Lists :  ");
            }
        });
        et_searchTask.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    HideKeyboard();
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listRecyclerAdapter.notifyDataSetChanged();

        if (searchAdapter != null)
            searchAdapter.notifyDataSetChanged();
    }

    protected void HideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }

}