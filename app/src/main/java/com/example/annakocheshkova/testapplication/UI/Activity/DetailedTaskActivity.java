package com.example.annakocheshkova.testapplication.UI.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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

import com.example.annakocheshkova.testapplication.MVC.Controller.SubTaskController;
import com.example.annakocheshkova.testapplication.MVC.View.SubTaskView;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Adapter.SubTaskAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * this activity displays list of all the subtasks
 */
public class DetailedTaskActivity extends AppCompatActivity implements SubTaskView {

    List<SubTask> subTasks;
    SubTaskAdapter subTaskAdapter;
    RecyclerView listView;
    View view;
    SubTaskController subTaskController;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);
        getViews();
        setContent();
    }

    /**
     * get all view items to work with later
     */
    void getViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (RecyclerView)findViewById(R.id.task_detailed_view);
        view = findViewById(R.id.content);
    }

    /**
     * set all content configuration and click listeners
     */
    void setContent() {
        subTaskController = new SubTaskController(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        List<SubTask> items = new ArrayList<>();
        subTaskAdapter = new SubTaskAdapter(items, subTaskController);
        listView.setAdapter(subTaskAdapter);
        subTaskController.getAllByTask(true, getIntent().getIntExtra("id", 0));
        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                SubTask simple = subTasks.get(pos);
                subTaskController.delete(simple);
                Snackbar.make(view, getString(R.string.deleted_string_firstpart)+" "+simple.getName()+" "+getString(R.string.deleted_string_secondpart), Snackbar.LENGTH_LONG).setAction(
                        getString(R.string.cancel_btn), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subTaskController.restoreDeleted();
                    }
                }).show();
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
            startActivity(new Intent(this, MainTasksActivity.class));
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
    public void showDialog(SubTask subTask) {
        AlertDialogFragment alertdFragment = new AlertDialogFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (subTask != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", subTask.getID());
            bundle.putString("name", subTask.getName());
            alertdFragment.setArguments(bundle);
        }
        alertdFragment.show(supportFragmentManager, getString(R.string.alertDialogFragmentTag));
    }

    /**
     * AlertDialogFragment callback, creates a new subtask in the database or updates it, if needed
     * @param name name of the created subtask
     */
    public void itemCreatedCallback(String name) {
            subTaskController.onEditingEnded(name);
    }
}
