package com.example.annakocheshkova.testapplication.UI.Adapter;

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
import com.example.annakocheshkova.testapplication.UI.Activity.DetailedTaskActivity;

import java.util.List;

/**
 * Adapter for subtask recycler view
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    static List<SubTask> dataset;
    static SubTaskController subTaskController;
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        TextView mTextView;
        int darkColor;
        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.row_text);
            darkColor = ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SubTask subTask = dataset.get(getAdapterPosition());
            subTaskController.onStatusChanged(subTask);
        }

        @Override
        public boolean onLongClick(View view) {
            subTaskController.onUpdate(dataset.get(getAdapterPosition()));
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
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_row,  null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(dataset.get(position).getName());
        if (dataset.get(position).getStatus())
            holder.mTextView.setTextColor(Color.GRAY);
        else
            holder.mTextView.setTextColor(holder.darkColor);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
