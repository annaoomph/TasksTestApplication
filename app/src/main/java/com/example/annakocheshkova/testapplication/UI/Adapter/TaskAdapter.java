package com.example.annakocheshkova.testapplication.UI.Adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.MVC.Controller.SubTaskController;
import com.example.annakocheshkova.testapplication.MVC.Controller.TaskController;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Activity.CreateItemActivity;
import com.example.annakocheshkova.testapplication.UI.Activity.SubTaskActivity;

import java.util.Calendar;
import java.util.List;

/**
 * Adapter for Tasks recycle view
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    /**
     * list of the tasks to be displayed by the adapter
     */
    private static List<Task> taskList;

    /**
     * subTasks controller working with the subTaskView
     */
    private static TaskController taskController;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

        /**
         * text with the name of the subtask
         */
        TextView textRow;

        /**
         * image that's shown when the task has alarms scheduled
         */
        ImageButton imageAlarmButton;

        /**
         * this color is used when the subtask is not completed
         */
        int darkColor;

        /**
         * this color is used when the subtask is completed
         */
        int lightColor;

        /**
         * this color is used when the task time has expired but it's not completed;
         */
        int redColor;

        /**
         * creates new instance of the viewholder for a certain row
         */
        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.row_text);
            imageAlarmButton = (ImageButton) view.findViewById(R.id.imageButton);
            darkColor = ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark);
            lightColor = ContextCompat.getColor(view.getContext(), R.color.completedColor);
            redColor = ContextCompat.getColor(view.getContext(), R.color.redColor);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            taskController.onItemChosen(taskList.get(getAdapterPosition()).getID());
        }

        @Override
        public boolean onLongClick(View view) {
            taskController.onItemUpdate(taskList.get(getAdapterPosition()).getID());
            return true;
        }
    }

    /**
     * creates new instance of the adapter
     * @param taskList list of the tasks to be displayed by adapter
     */
    public TaskAdapter(List<Task> taskList, TaskController taskController) {
        TaskAdapter.taskList = taskList;
        TaskAdapter.taskController = taskController;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, null);
        return new ViewHolder(itemLayoutView);
    }

    /**
     * a method called everytime when data changes
     * @param newItems new data
     */
    public void changeData(List<Task> newItems) {
        taskList = newItems;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task item = taskList.get(position);
        holder.textRow.setText(item.getName());
        switch (item.getStatus()) {
            case Completed: holder.textRow.setTextColor(holder.lightColor); break;
            case Pending: holder.textRow.setTextColor(holder.darkColor); break;
            case Uncompleted: holder.textRow.setTextColor(holder.redColor); break;

        }
        if (taskList.get(position).hasAlarms()) {
            holder.imageAlarmButton.setVisibility(View.VISIBLE);
        } else {
            holder.imageAlarmButton.setVisibility(View.GONE);
        }
    }

    /**
     * get the task by its position in the list of items
     * @param position of the task
     * @return task
     */
    public Task getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
