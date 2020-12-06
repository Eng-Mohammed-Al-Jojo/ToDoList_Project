package com.example.project_lab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.project_lab.Utils.staticData;

import java.util.ArrayList;

import static com.example.project_lab.Utils.staticData.list;


public class task_list_Activity extends AppCompatActivity {

    RecyclerView taskRecycler;
    taskRecyclerAdapter taskRecyclerAdapter;
    ItemList listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_);

        int pos = getIntent().getIntExtra("clickedPosition", -1);
        listItem = staticData.list.get(pos);

        taskRecycler = findViewById(R.id.taskRecycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));

        taskRecyclerAdapter = new taskRecyclerAdapter(task_list_Activity.this, listItem);
        taskRecycler.setAdapter(taskRecyclerAdapter);

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
                    listItem.getTasks().add(new Task(titleText, "", "", false));
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
                        listItem.getTasks().add(new Task(titleText, "", "", false));
                        et_create_task.getText().clear();
                        HideKeyboard();
                        taskRecyclerAdapter.notifyDataSetChanged();
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
                new AlertDialog.Builder(activity)
                        .setTitle("Delete: " + listItem.getTitle() + " !")
                        .setMessage("Are you sure about delete this list ?\n its will delete " + listItem.getTasks().size() + " task!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                staticData.list.remove(listItem);
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