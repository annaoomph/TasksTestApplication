package com.example.annakocheshkova.testapplication.Views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.R;

import java.util.List;

/**
 * Adapter for Tasks recycle view
 */
class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> dataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageButton imgBtn;
        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.row_text);
            imgBtn = (ImageButton) view.findViewById(R.id.imageButton);
        }
    }

    TaskAdapter(List<Task> myDataset) {
        dataset = myDataset;
 }

 @Override
 public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, null);
     return new ViewHolder(itemLayoutView);
 }

 @Override
 public void onBindViewHolder(ViewHolder holder, int position) {
     holder.mTextView.setText(dataset.get(position).getName());
     if (dataset.get(position).hasAlarms())
         holder.imgBtn.setVisibility(View.VISIBLE);
     else
         holder.imgBtn.setVisibility(View.GONE);
}
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
