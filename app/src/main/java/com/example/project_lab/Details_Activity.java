package com.example.project_lab;

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

import java.util.Calendar;

public class Details_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView tv_Date;
    Button button_Date;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        Button btn_delete_task = (Button) findViewById(R.id.btn_delete_task);
        Button btn_update_details = (Button) findViewById(R.id.btn_update_details);
        btn_update_details.setVisibility(View.GONE);
        btn_delete_task.setVisibility(View.VISIBLE);
        button_Date = findViewById(R.id.calender_date);
        button_Date.setVisibility(View.GONE);

        EditText et_detailsTitle = (EditText) findViewById(R.id.et_detailsTitle);
        EditText et_detailsDescription = (EditText) findViewById(R.id.et_detailsDescription);
        et_detailsTitle.setEnabled(false);
        et_detailsDescription.setEnabled(false);


        TextView cancel_update = (TextView) findViewById(R.id.cancel_update);
        cancel_update.setVisibility(View.GONE);

        int listId = getIntent().getIntExtra("listId", -1);
        int taskPosition = getIntent().getIntExtra("taskPosition", -1);

        ItemList listItem = staticData.list.get(listId - 1);
        Task task = listItem.getTasks().get(taskPosition);

        TextView tv_detailsLabel = (TextView) findViewById(R.id.tv_detailsLabel);
        TextView tv_detailsDate = (TextView) findViewById(R.id.tv_detailsDate);

        et_detailsTitle.setText(task.getTitle());
        tv_detailsLabel.setText(listItem.getTitle());
        tv_detailsDate.setText(task.getDate());
        et_detailsDescription.setText(task.getDescription());

        Activity activity = this;

        TextView go_to_Update = (TextView) findViewById(R.id.go_to_Update);
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
                                listItem.getTasks().remove(task);
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
                onBackPressed();
            }
        });

        Button btn_back_to_taskList = (Button) findViewById(R.id.btn_back_to_taskList);
        btn_back_to_taskList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

                tv_detailsLabel.setText(listItem.getTitle());
                et_detailsTitle.setText(task.getTitle());
                tv_detailsDate.setText(task.getDate());
                et_detailsDescription.setText(task.getDescription());
            }
        });




        /*     Date & Time Calender      */


        tv_Date = findViewById(R.id.tv_detailsDate);

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
}