package com.example.annakocheshkova.testapplication.UI.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.MVC.Controller.SubTaskController;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Activity.AlertDialogFragment;
import com.example.annakocheshkova.testapplication.UI.Activity.CreateItemActivity;
import com.example.annakocheshkova.testapplication.UI.Activity.DetailedTaskActivity;
import com.example.annakocheshkova.testapplication.UI.Activity.MainTasksActivity;

import java.util.List;

/**
 * Adapter for subtask recycler view
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    static List<SubTask> dataset;
    static SubTaskController subTaskController;
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        TextView mTextView;
        ImageButton imgBtn;
        int darkColor;
        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.row_text);
            imgBtn = (ImageButton) view.findViewById(R.id.imageButton);
            darkColor = ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SubTask subTask = dataset.get(getAdapterPosition());
            subTask.setStatus(!subTask.getStatus());
            subTaskController.update(subTask);
        }

        @Override
        public boolean onLongClick(View view) {
            AlertDialogFragment alertdFragment = new AlertDialogFragment();
            //TODO Remove this?
            DetailedTaskActivity activity = (DetailedTaskActivity)view.getContext();
            FragmentManager fm = activity.getSupportFragmentManager();
            alertdFragment.show(fm, "Alert Dialog Fragment");
            Bundle bundle = new Bundle();
            bundle.putInt("id",getAdapterPosition());
            bundle.putString("name",dataset.get(getAdapterPosition()).getName());
            alertdFragment.setArguments(bundle);
            return true;
        }
    }

    public SubTaskAdapter(List<SubTask> myDataset, SubTaskController subTaskController) {
        this.subTaskController = subTaskController;
        dataset = myDataset;
    }

    public void changeData(List<SubTask> newItems) {
        dataset = newItems;
        this.notifyDataSetChanged();
    }

    @Override
    public SubTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(dataset.get(position).getName());
        if (dataset.get(position).getStatus())
            holder.mTextView.setTextColor(Color.GRAY);
        else
            holder.mTextView.setTextColor(holder.darkColor);
        holder.imgBtn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
