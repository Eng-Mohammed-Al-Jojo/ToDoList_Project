package com.example.project_lab.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_lab.Details_Activity;
import com.example.project_lab.Models.ItemList;
import com.example.project_lab.Models.Task;
import com.example.project_lab.R;
import com.example.project_lab.task_list_Activity;

import java.util.ArrayList;

public class taskRecyclerAdapter extends RecyclerView.Adapter<taskRecyclerAdapter.ItemTaskViewHolder> {

    Activity activity;
    ItemList data;

    public taskRecyclerAdapter(Activity activity, ItemList data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public ItemTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.itemtask_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new taskRecyclerAdapter.ItemTaskViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTaskViewHolder holder, int position) {
        holder.tv_taskTitle.setText(data.getTasks().get(position).getTitle());
        holder.cb_taskChecked.setChecked(data.getTasks().get(position).isChecked());
        if(data.getTasks().get(position).isChecked()){
            holder.tv_taskTitle.setPaintFlags(holder.tv_taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.cb_taskChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.getTasks().get(position).setChecked(isChecked);
                if(isChecked){
                    holder.tv_taskTitle.setPaintFlags(holder.tv_taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.tv_taskTitle.setPaintFlags(holder.tv_taskTitle.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Details_Activity.class);
                intent.putExtra("listId", data.getId());
                intent.putExtra("taskPosition", position);
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.getTasks().size();
    }

    public static class ItemTaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_taskTitle;
         private  final CheckBox cb_taskChecked;

        public ItemTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_taskTitle = itemView.findViewById(R.id.tv_taskTitle);
            cb_taskChecked = itemView.findViewById(R.id.cb_taskChecked);
        }
    }
    }