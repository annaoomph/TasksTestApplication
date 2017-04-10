package com.example.annakocheshkova.testapplication.UI.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.MVC.Controller.SubTaskController;
import com.example.annakocheshkova.testapplication.MVC.View.SubTaskView;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Adapter.SubTaskAdapter;
import com.example.annakocheshkova.testapplication.Utils.Component.UndoComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * this activity displays list of all the subtasks
 */
public class SubTaskActivity extends AppCompatActivity implements SubTaskView {

    /**
     * adapter for the recycler view
     */
    SubTaskAdapter subTaskAdapter;

    /**
     * main view with all the content
     */
    View mainView;

    /**
     * main controller for the view
     */
    SubTaskController subTaskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);
        setContent();
    }

    /**
     * sets all content configuration and click listeners
     */
    void setContent() {
        RecyclerView listView = (RecyclerView)findViewById(R.id.task_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subTaskController = new SubTaskController(this);
        mainView = findViewById(R.id.content);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        List<SubTask> items = new ArrayList<>();
        subTaskAdapter = new SubTaskAdapter(items, subTaskController);
        listView.setAdapter(subTaskAdapter);
        subTaskController.onViewLoaded(getIntent().getIntExtra("id", 0));
        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            subTaskController.onCreate();
        }
        if (id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showItems(List<SubTask> items) {
        subTaskAdapter.changeData(items);
    }

    @Override
    public void showTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(title);
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
        alertdFragment.show(supportFragmentManager, getString(R.string.alertDialogFragmentTag));
    }

    @Override
    public void showCancelBar(SubTask subTask) {
        UndoComponent<SubTask> undoComponent = new UndoComponent<>();
        undoComponent.make(mainView, subTask, subTaskController, subTask.getName());
    }

    @Override
    public void showNoSuchTaskError() {
        Toast.makeText(this, MyApplication.getAppContext().getString(R.string.no_such_task), Toast.LENGTH_LONG).show();
        finish();
    }
}
