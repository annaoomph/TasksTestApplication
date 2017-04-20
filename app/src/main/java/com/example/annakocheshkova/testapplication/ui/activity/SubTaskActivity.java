package com.example.annakocheshkova.testapplication.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import com.example.annakocheshkova.testapplication.mvc.controller.SubTaskController;
import com.example.annakocheshkova.testapplication.mvc.view.SubTaskView;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.ui.adapter.SubTaskAdapter;
import com.example.annakocheshkova.testapplication.utils.component.UndoComponent;
import java.util.List;

/**
 * An activity that displays list of all the subtasks
 */
public class SubTaskActivity extends BaseActivity implements SubTaskView {

    /**
     * Adapter for the recycler view
     */
    SubTaskAdapter subTaskAdapter;

    /**
     * Main view with all the content
     */
    View mainView;

    /**
     * Main controller for the view
     */
    SubTaskController subTaskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);
        setContent();
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            subTaskController.onCreate();
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets all content configuration and click listeners
     */
    void setContent() {
        RecyclerView listView = (RecyclerView)findViewById(R.id.task_detailed_view);
        subTaskController = new SubTaskController(this);
        mainView = findViewById(R.id.content);
        subTaskAdapter = new SubTaskAdapter(subTaskController);
        listView.setAdapter(subTaskAdapter);
        subTaskController.onViewLoaded(getIntent().getIntExtra("id", -1));
        listView.setHasFixedSize(true); //for better performance
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                subTaskController.onDelete(subTaskAdapter.getItem(pos));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listView);
    }

    @Override
    public void showItems(List<SubTask> items) {
        subTaskAdapter.setData(items);
    }

    @Override
    public void showTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void showDialog(SubTask subTask, int taskId) {
        AlertDialogFragment alertdFragment = new AlertDialogFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        if (subTask != null) {
            bundle.putInt("id", subTask.getID());
        }
        bundle.putInt("taskId",  taskId);
        alertdFragment.setArguments(bundle);
        alertdFragment.setOnItemEditedListener(subTaskController);
        alertdFragment.show(supportFragmentManager, getString(R.string.alert_dialog_fragment_tag));
    }

    @Override
    public void showCancelBar(SubTask subTask) {
        UndoComponent<SubTask> undoComponent = new UndoComponent<>();
        undoComponent.make(mainView, subTask, subTaskController, subTask.getName());
    }

    @Override
    public void showNoSuchTaskError() {
        showToast(getString(R.string.no_such_task_error));
        finish();
    }
}
