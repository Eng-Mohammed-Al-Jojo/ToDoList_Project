package com.example.project_lab.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_lab.Adapters.listRecyclerAdapter;
import com.example.project_lab.Adapters.taskRecyclerAdapter;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FirebaseConnection {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String uid;
    public ArrayList<ItemList> lists = new ArrayList<>();
    public ArrayList<Task> tasks = new ArrayList<>();

    public FirebaseConnection() {

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        this.uid = mAuth.getCurrentUser().getUid();
    }

    public void getLists(listRecyclerAdapter listRecyclerAdapter) {

        database.getReference("users/"+uid+"/lists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                 for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                     ItemList itemList = dataSnapshot.getValue(ItemList.class);
                     lists.add(itemList);
                 }
                listRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    public void addList(ItemList itemList) {
        DatabaseReference mRef = database.getReference("users/"+uid+"/lists").push();
        String key = mRef.getKey();

        itemList.setId(key);

        database.getReference("users/"+uid+"/lists/"+key).setValue(itemList);
    }

    public void getTasks(taskRecyclerAdapter taskRecyclerAdapter, String listId) {

        database.getReference("users/"+uid+"/tasks").orderByChild("listId").equalTo(listId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasks.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Task task = dataSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
                taskRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    public void addTask(Task task, String listId) {
        DatabaseReference mRef = database.getReference("users/"+uid+"/tasks").push();
        String key = mRef.getKey();

        task.setId(key);
        task.setListId(listId);

        // Current Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateAndTime = formatter.format(new Date());

        task.setDate(dateAndTime);

        database.getReference("users/"+uid+"/tasks/"+key).setValue(task);

        database.getReference("users/"+uid+"/lists/"+listId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemList itemList = snapshot.getValue(ItemList.class);
                itemList.setNumOfTasks(itemList.getNumOfTasks()+1);
                database.getReference("users/"+uid+"/lists/"+listId).setValue(itemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void updateTask(Task task) {
        database.getReference("users/"+uid+"/tasks/"+task.getId()).setValue(task);
        database.getReference("users/"+uid+"/lists/"+task.getListId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemList itemList = snapshot.getValue(ItemList.class);
                itemList.setNumOfTasks(itemList.getNumOfTasks());
                database.getReference("users/"+uid+"/lists/"+task.getListId()).setValue(itemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }



}
