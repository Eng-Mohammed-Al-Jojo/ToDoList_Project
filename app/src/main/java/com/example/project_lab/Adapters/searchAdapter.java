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
import com.example.project_lab.Models.searchTask;
import com.example.project_lab.R;
import com.example.project_lab.Utils.staticData;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.searchItemViewHolder> {

    Activity activity;
    ArrayList<searchTask> data = new ArrayList<searchTask>();

    public searchAdapter(Activity activity, ArrayList<searchTask> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public searchAdapter.searchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.search_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new searchItemViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdapter.searchItemViewHolder holder, int position) {
        searchTask search_task = data.get(position);
        ItemList itemList = staticData.list.get(search_task.getItemListId()-1);
        Task task = itemList.getTasks().get(search_task.getTaskId());

        holder.tv_itemListTitle.setText(itemList.getTitle());
        holder.tv_taskTitle.setText(task.getTitle());
        holder.cb_taskChecked.setChecked(task.isChecked());
        if(task.isChecked()){
            holder.tv_taskTitle.setPaintFlags(holder.tv_taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.cb_taskChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setChecked(isChecked);
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
                intent.putExtra("listId", data.get(position).getItemListId());
                intent.putExtra("taskPosition",data.get(position).getTaskId());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class searchItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_taskTitle;
        private final TextView tv_itemListTitle;
        private  final CheckBox cb_taskChecked;


        public searchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_taskTitle = itemView.findViewById(R.id.tv_taskTitle);
            tv_itemListTitle =itemView.findViewById(R.id.tv_itemListTitle);
            cb_taskChecked = itemView.findViewById(R.id.cb_taskChecked);
        }
    }
}
