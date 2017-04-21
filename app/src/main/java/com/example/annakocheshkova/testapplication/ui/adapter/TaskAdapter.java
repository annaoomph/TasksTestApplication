package com.example.annakocheshkova.testapplication.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.annakocheshkova.testapplication.mvc.controller.TaskController;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adapter for Tasks recycle view
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    /**
     * List of the tasks to be displayed by the adapter
     */
    private List<Task> taskList;

    /**
     * SubTasks controller working with the subTaskView
     */
    private TaskController taskController;

    /**
     * A color to draw a task that is not completed
     */
    private final int darkColor;

    /**
     * A color to draw a task that is completed
     */
    private final int lightColor;

    /**
     * a color to draw a task which time has expired but it's not completed;
     */
    private final int redColor;

    /**
     * Creates new instance of the adapter
     * @param taskController controller of the parent view
     */
    public TaskAdapter(TaskController taskController) {
        this.taskList = new ArrayList<>();
        this.taskController = taskController;
        darkColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.textColorPrimary);
        lightColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.completedColor);
        redColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.redColor);
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, null);
        return new ViewHolder(itemLayoutView);
    }

    /**
     * A method called everytime when data changes
     * @param newItems new data
     */
    public void setData(List<Task> newItems) {
        taskList = newItems;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Task item = taskList.get(position);
        holder.textRow.setText(item.getName());
        switch (item.getStatus()) {
            case Completed: holder.textRow.setTextColor(lightColor); break;
            case Pending: holder.textRow.setTextColor(darkColor); break;
            case Expired: holder.textRow.setTextColor(redColor); break;
        }
        if (item.hasAlarm()) {
            holder.timeText.setText(getDateTimeString(item.getAlarmTime()));
            holder.alarmLayout.setVisibility(View.VISIBLE);
        } else {
            holder.alarmLayout.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskController.onItemChosen(taskList.get(holder.getAdapterPosition()).getID());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                taskController.onItemUpdate(taskList.get(holder.getAdapterPosition()).getID());
                return true;
            }
        });
    }

    /**
     * Gets the task by its position in the list of items
     * @param position of the task
     * @return task
     */
    public Task getItem(int position) {
        return taskList.get(position);
    }

    /**
     * Gets date and time of the task in string format
     * @param time task time
     * @return date and time in string format
     */
    private String getDateTimeString(long time) {
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        Date date = new Date(time);
        return format.format(date);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * A text with the name of the subtask
         */
        TextView textRow;

        /**
         * A text with alarm time
         */
        TextView timeText;

        /**
         * Layout that's shown when the task has alarms scheduled
         */
        LinearLayout alarmLayout;

        /**
         * Creates new instance of a view holder
         * @param view main view
         */
        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.row_text);
            alarmLayout = (LinearLayout) view.findViewById(R.id.alarm_info);
            timeText = (TextView)view.findViewById(R.id.time_text);
        }
    }
}
