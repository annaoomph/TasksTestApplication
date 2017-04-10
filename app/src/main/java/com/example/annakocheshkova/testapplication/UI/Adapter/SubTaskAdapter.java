package com.example.annakocheshkova.testapplication.UI.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.annakocheshkova.testapplication.MVC.Controller.SubTaskController;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import java.util.List;

/**
 * Adapter for subtask recycler view
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {

    /**
     * a list of the subtasks to be displayed by the adapter
     */
    private static List<SubTask> subTasksList;

    /**
     * subTasks controller working with the subTaskView
     */
    private static SubTaskController subTaskController;

    /**
     * this color is used when the subtask is not completed
     */
    private final int darkColor;

    /**
     * this color is used when the subtask is completed
     */
    private final int lightColor;

    /**
     * creates new instance of adapter
     * @param data list of subtasks to be shown
     * @param subTaskController controller of the subtasks view
     */
    public SubTaskAdapter(List<SubTask> data, SubTaskController subTaskController) {
        SubTaskAdapter.subTaskController = subTaskController;
        subTasksList = data;
        darkColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.textColorPrimary);
        lightColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.completedColor);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

        /**
         * textView displays the name of the subtask
         */
        TextView textRow;

        /**
         * creates new instance of the viewholder for a certain row
         */
        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.row_text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SubTask subTask = subTasksList.get(getAdapterPosition());
            subTaskController.onStatusChanged(subTask);
        }

        @Override
        public boolean onLongClick(View view) {
            subTaskController.onUpdate(subTasksList.get(getAdapterPosition()));
            return true;
        }
    }

    /**
     * a method called everytime when data changes
     * @param newItems new data
     */
    public void changeData(List<SubTask> newItems) {
        subTasksList = newItems;
        this.notifyDataSetChanged();
    }

    /**
     * get the subtask by its position in the list of items
     * @param position of the subtask
     * @return subtask
     */
    public SubTask getItem(int position) {
        return subTasksList.get(position);
    }

    @Override
    public SubTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_row,  null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textRow.setText(subTasksList.get(position).getName());
        if (subTasksList.get(position).getStatus()) {
            holder.textRow.setTextColor(lightColor);
        } else {
            holder.textRow.setTextColor(darkColor);
        }
    }

    @Override
    public int getItemCount() {
        return subTasksList.size();
    }
}
