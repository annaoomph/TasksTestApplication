package com.example.annakocheshkova.testapplication.UI.Adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
         * text with alarm time
         */
        TextView timeText;

        /**
         * image that's shown when the task has alarms scheduled
         */
        LinearLayout alarmLayout;

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

        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.row_text);
            alarmLayout = (LinearLayout) view.findViewById(R.id.alarm_info);
            timeText = (TextView)view.findViewById(R.id.time_text);
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
     * constructor
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
     * a method called everytime when the data changes
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
        if (item.hasAlarms()) {
            holder.timeText.setText(getDateTimeString(item.getAlarmTime()));
            holder.alarmLayout.setVisibility(View.VISIBLE);
        } else {
            holder.alarmLayout.setVisibility(View.GONE);
        }
    }

    /**
     * get date and time of the task in string format
     * @param time task time
     * @return date and time in string format
     */
    private String getDateTimeString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1)
            day = "0" + day;
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        if (month.length() == 1)
            month = "0" + month;
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        if (year.length() == 1)
            year = "0" + year;
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if (hour.length() == 1)
            hour = "0" + hour;
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if (minute.length() == 1)
            minute = "0" + minute;
        return hour + ":" + minute + " " + day + "-" + month + "-" + year;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
