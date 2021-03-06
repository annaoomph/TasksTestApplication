package com.example.annakocheshkova.testapplication.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.annakocheshkova.testapplication.mvc.controller.SubTaskController;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for subtask recycler view
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {

    /**
     * A list of the subtasks to be displayed by the adapter
     */
    private List<SubTask> subTasksList;

    /**
     * SubTasks controller working with the subTaskView
     */
    private SubTaskController subTaskController;

    /**
     * A color used when the subtask is not completed
     */
    private final int darkColor;

    /**
     * A color used when the subtask is completed
     */
    private final int lightColor;

    /**
     * Creates new instance of adapter
     * @param subTaskController controller of the subtasks view
     */
    public SubTaskAdapter(SubTaskController subTaskController) {
        this.subTaskController = subTaskController;
        subTasksList = new ArrayList<>();
        darkColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.textColorPrimary);
        lightColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.completedColor);
    }

    /**
     * Called everytime when data changes
     * @param newItems new data
     */
    public void setData(List<SubTask> newItems) {
        subTasksList = newItems;
        this.notifyDataSetChanged();
    }

    /**
     * Gets the subtask by its position in the list of items
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textRow.setText(subTasksList.get(position).getName());
        if (subTasksList.get(position).getStatus()) {
            holder.textRow.setTextColor(lightColor);
        } else {
            holder.textRow.setTextColor(darkColor);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubTask subTask = subTasksList.get(holder.getAdapterPosition());
                subTaskController.onStatusChanged(subTask);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                subTaskController.onUpdate(subTasksList.get(holder.getAdapterPosition()));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return subTasksList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * A textView that displays the name of the subtask
         */
        TextView textRow;

        /**
         * Creates new instance of a view holder for a certain row
         * @param view main view
         */
        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.row_text);
        }
    }
}
