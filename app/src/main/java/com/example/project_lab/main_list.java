package com.example.project_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Adapters.searchAdapter;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Utils.staticData;

import java.util.ArrayList;

import static com.example.project_lab.Utils.staticData.list;

public class main_list extends AppCompatActivity {

    RecyclerView listRecycler;
    public listRecyclerAdapter listRecyclerAdapter;
    public searchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        listRecycler = findViewById(R.id.listRecycler);
        listRecycler.setLayoutManager(new LinearLayoutManager(this));

        listRecyclerAdapter = new listRecyclerAdapter(main_list.this, staticData.list);
        listRecycler.setAdapter(listRecyclerAdapter);


        /*  Create New Item list*/

        EditText et_create_list = (EditText) findViewById(R.id.et_create_list);
        ImageButton btn_new_list = (ImageButton) findViewById(R.id.btn_new_list);
        btn_new_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = et_create_list.getText().toString().trim();
                if (titleText.isEmpty()) {
                    et_create_list.setError("this can not be empty");
                } else {
                    list.add(new ItemList((list.size() + 1), titleText, new ArrayList<Task>()));
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
                        list.add(new ItemList((list.size() + 1), titleText, new ArrayList<Task>()));
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

        EditText et_searchTask = (EditText) findViewById(R.id.et_searchTask);
        ImageButton btn_searchTask = (ImageButton) findViewById(R.id.btn_searchTask);
        btn_searchTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String searchText = et_searchTask.getText().toString().trim();
                if (searchText.isEmpty()) {
                    et_searchTask.setError("Con not be empty!");
                    listRecycler.setAdapter(listRecyclerAdapter);


                } else {
                    searchAdapter = new searchAdapter(main_list.this, staticData.searchTask(searchText));
                    listRecycler.setAdapter(searchAdapter);

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
                    searchAdapter = new searchAdapter(main_list.this, staticData.searchTask(searchText));
                    listRecycler.setAdapter(searchAdapter);
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