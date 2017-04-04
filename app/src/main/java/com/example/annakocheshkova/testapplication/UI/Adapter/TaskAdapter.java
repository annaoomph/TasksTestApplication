package com.example.annakocheshkova.testapplication.UI.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Activity.CreateItemActivity;
import com.example.annakocheshkova.testapplication.UI.Activity.DetailedTaskActivity;

import java.util.List;

/**
 * Adapter for Tasks recycle view
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    static List<Task> dataset;
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        TextView mTextView;
        ImageButton imgBtn;
        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.row_text);
            imgBtn = (ImageButton) view.findViewById(R.id.imageButton);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO Remove this
            Intent intent = new Intent(view.getContext(), DetailedTaskActivity.class);
            intent.putExtra("id", dataset.get(getAdapterPosition()).getID());
            view.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            Intent intent = new Intent(view.getContext(), CreateItemActivity.class);
            intent.putExtra("id", dataset.get(getAdapterPosition()).getID());
            view.getContext().startActivity(intent);
            return true;
        }
    }

    public TaskAdapter(List<Task> myDataset) {
        dataset = myDataset;
 }

 @Override
 public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, null);
     return new ViewHolder(itemLayoutView);
 }

    public void changeData(List<Task> newItems) {
        dataset = newItems;
        this.notifyDataSetChanged();
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
