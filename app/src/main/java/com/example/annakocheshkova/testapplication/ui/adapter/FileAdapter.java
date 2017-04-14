package com.example.annakocheshkova.testapplication.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * An adapter for recycler file view
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>  {
    /**
     * A list of the subtasks to be displayed by the adapter
     */
    private List<File> fileList;

    /**
     * A color to draw all the items that are not chosen
     */
    private static int lightColor;

    /**
     * A color used when the file is chosen
     */
    private static int chosenColor;

    /**
     * The position of chosen file
     */
    private int chosenPosition;

    /**
     * Creates new instance of adapter
     */
    public FileAdapter() {
        fileList = new ArrayList<>();
        chosenPosition = -1;
        lightColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.completedColor);
        chosenColor = ContextCompat.getColor(MyApplication.getAppContext(), R.color.textColorPrimary);
    }

    /**
     * Called everytime when data changes
     * @param newItems new data
     */
    public void changeData(List<File> newItems) {
        chosenPosition = -1;
        fileList = newItems;
        this.notifyDataSetChanged();
    }

    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_row,  null);
        return new FileAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(FileAdapter.ViewHolder holder, int position) {
        holder.textRow.setText(fileList.get(position).getName());
        if (chosenPosition == position) {
            holder.textRow.setTextColor(chosenColor);
            holder.chosenSign.setVisibility(View.VISIBLE);
        }
        else {
            holder.textRow.setTextColor(lightColor);
            holder.chosenSign.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    /**
     * Gets the path to the item user has chosen
     * @return path
     */
    public String getChosenItemPath() {
        if (chosenPosition < 0)
            return "";
        return fileList.get(chosenPosition).getPath();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        /**
         * A textView that displays the name of the subtask
         */
        TextView textRow;

        /**
         * A stripe shown when the file is selected
         */
        ImageButton chosenSign;

        /**
         * Creates new instance of a view holder for a certain row
         * @param view main view
         */
        ViewHolder(View view) {
            super(view);
            textRow = (TextView) view.findViewById(R.id.file_row_text);
            chosenSign = (ImageButton) view.findViewById(R.id.chosen_sign);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            chosenPosition = getAdapterPosition();
            notifyDataSetChanged();
        }
    }
}
