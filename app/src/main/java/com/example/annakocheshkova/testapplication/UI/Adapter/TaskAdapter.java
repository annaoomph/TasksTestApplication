package com.example.annakocheshkova.testapplication.UI.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.MVC.Controller.TaskController;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

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

    /**
     * a color to draw a task that is not completed
     */
    private final int darkColor;

    /**
     * a color to draw a task that is completed
     */
    private final int lightColor;

    /**
     * a color to draw a task which time has expired but it's not completed;
     */
    private final int redColor;

    /**
     * creates new instance of the adapter
     * @param taskList list of the tasks to be displayed by adapter
     */
    public TaskAdapter(List<Task> taskList, TaskController taskController) {
        TaskAdapter.taskList = taskList;
        TaskAdapter.taskController = taskController;
        darkColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.textColorPrimary);
        lightColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.completedColor);
        redColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.redColor);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

        /**
         * a text with the name of the subtask
         */
        TextView textRow;

        /**
         * an image that's shown when the task has alarms scheduled
         */
        ImageButton imageAlarmButton;

        /**
         * creates new instance of a viewholder
         * @param view main view
         */
        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.row_text);
            imageAlarmButton = (ImageButton) view.findViewById(R.id.imageButton);
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
            case Completed: holder.textRow.setTextColor(lightColor); break;
            case Pending: holder.textRow.setTextColor(darkColor); break;
            case Uncompleted: holder.textRow.setTextColor(redColor); break;

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
