package com.example.annakocheshkova.testapplication.Views;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.R;

import java.util.List;

/**
 * Adapter for subtask recucler view
 */
class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    private List<SubTask> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageButton imgBtn;
        int darkColor;
        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.row_text);
            imgBtn = (ImageButton) view.findViewById(R.id.imageButton);
            //TODO Change to non-deprecated method
            darkColor = view.getResources().getColor(R.color.colorPrimaryDark);
        }
    }

    SubTaskAdapter(List<SubTask> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public SubTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).getName());
        if (mDataset.get(position).getStatus())
            holder.mTextView.setTextColor(Color.GRAY);
        else
            holder.mTextView.setTextColor(holder.darkColor);
        holder.imgBtn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
