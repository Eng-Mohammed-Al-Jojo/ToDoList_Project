package com.example.project_lab.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_lab.Models.ItemList;
import com.example.project_lab.R;
import com.example.project_lab.task_list_Activity;

import java.util.ArrayList;

public class listRecyclerAdapter extends RecyclerView.Adapter<listRecyclerAdapter.ItemListViewHolder> {

    Activity activity;
    ArrayList<ItemList> data;

    public listRecyclerAdapter(Activity activity, ArrayList<ItemList> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public listRecyclerAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.itemlist_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new ItemListViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull listRecyclerAdapter.ItemListViewHolder holder, int position) {
        holder.tv_itemListTitle.setText(data.get(position).getTitle());
        holder.tv_itemListCount.setText(data.get(position).getTasks().size() + " Tasks");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, task_list_Activity.class);
                intent.putExtra("clickedPosition", position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_itemListTitle;
        public TextView tv_itemListCount;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemListTitle = itemView.findViewById(R.id.tv_itemListTitle);
            tv_itemListCount = itemView.findViewById(R.id.tv_itemListCount);
        }
    }
}
