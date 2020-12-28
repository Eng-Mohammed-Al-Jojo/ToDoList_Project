package com.example.project_lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.Utils.staticData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Details_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView tv_Date;
    Button button_Date,btn_back_to_taskList;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    String taskId;
    TextView tv_detailsLabel, tv_detailsDate, go_to_Update, cancel_update;
    Button btn_delete_task, btn_update_details;
    EditText et_detailsTitle, et_detailsDescription;
    String uid;
    String itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        taskId = getIntent().getStringExtra("taskId");

        btn_back_to_taskList =  findViewById(R.id.btn_back_to_taskList);
        btn_delete_task =  findViewById(R.id.btn_delete_task);
        btn_update_details =  findViewById(R.id.btn_update_details);
        btn_update_details.setVisibility(View.GONE);
        btn_delete_task.setVisibility(View.VISIBLE);
        button_Date = findViewById(R.id.calender_date);
        button_Date.setVisibility(View.GONE);
        tv_detailsLabel = findViewById(R.id.tv_detailsLabel);
        tv_detailsDate = findViewById(R.id.tv_detailsDate);
        go_to_Update = findViewById(R.id.go_to_Update);

        et_detailsTitle =  findViewById(R.id.et_detailsTitle);
        et_detailsDescription =  findViewById(R.id.et_detailsDescription);
        et_detailsTitle.setEnabled(false);
        et_detailsDescription.setEnabled(false);

        cancel_update =  findViewById(R.id.cancel_update);
        cancel_update.setVisibility(View.GONE);
        tv_Date = findViewById(R.id.tv_detailsDate);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks/"+taskId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Task task = snapshot.getValue(Task.class);
                updateUI(task,itemList);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        btn_back_to_taskList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Details_Activity.this, Details_Activity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        tv_Date.setText(myYear + "/" + myMonth + "/" + myday + "  " + myHour + " : " + myMinute);
    }

    private void updateUI(Task task, String itemList)  {
        et_detailsTitle.setText(task.getTitle());
        FirebaseDatabase.getInstance().getReference("users/"+uid+"/lists/"+task.getListId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String category = snapshot.getValue(ItemList.class).getTitle();
                tv_detailsLabel.setText(category);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        tv_detailsDate.setText(task.getDate());
        et_detailsDescription.setText(task.getDescription());


        Activity activity = this;

        go_to_Update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn_delete_task.setVisibility(View.GONE);
                btn_update_details.setVisibility(View.VISIBLE);
                button_Date.setVisibility(View.VISIBLE);
                et_detailsTitle.setEnabled(true);
                et_detailsDescription.setEnabled(true);
                go_to_Update.setVisibility(View.GONE);
                cancel_update.setVisibility(View.VISIBLE);

            }
        });

        btn_delete_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setTitle("Delete: " + task.getTitle() + " !")
                        .setMessage("Are you sure about delete this task ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks/"+taskId).removeValue();
                                FirebaseDatabase.getInstance().getReference("users/"+uid+"/lists/"+task.getListId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        ItemList itemList = snapshot.getValue(ItemList.class);
                                        itemList.setNumOfTasks(itemList.getNumOfTasks()-1);
                                        FirebaseDatabase.getInstance().getReference("users/"+uid+"/lists/"+task.getListId()).setValue(itemList);
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

        btn_update_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newTitle = et_detailsTitle.getText().toString();
                String newDate = tv_detailsDate.getText().toString();
                String newDescription = et_detailsDescription.getText().toString();
                if (newTitle.isEmpty()) {
                    tv_detailsDate.setError("this can not be empty");
                    return;
                }
                if (newDescription.isEmpty()) {
                    et_detailsDescription.setError("this can not be empty");
                    return;
                }
                task.setTitle(newTitle);
                task.setDate(newDate);
                task.setDescription(newDescription);

                FirebaseDatabase.getInstance().getReference("users/"+uid+"/tasks/"+taskId).setValue(task);
                onBackPressed();
            }
        });

        cancel_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_delete_task.setVisibility(View.VISIBLE);
                btn_update_details.setVisibility(View.GONE);
                button_Date.setVisibility(View.GONE);
                et_detailsTitle.setEnabled(false);
                et_detailsDescription.setEnabled(false);
                go_to_Update.setVisibility(View.VISIBLE);
                cancel_update.setVisibility(View.GONE);

                tv_detailsLabel.setText(task.getTitle());
                et_detailsTitle.setText(task.getTitle());
                tv_detailsDate.setText(task.getDate());
                et_detailsDescription.setText(task.getDescription());
            }
        });

        /*     Date & Time Calender      */
        button_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Details_Activity.this, Details_Activity.this, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}