package com.example.annakocheshkova.testapplication.Views;

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

import com.example.annakocheshkova.testapplication.Controllers.SubTaskController;
import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.R;


import java.util.List;

public class DetailedTaskActivity extends AppCompatActivity implements SubTaskView{

    FragmentManager fm = getSupportFragmentManager();
    List<SubTask> subTasks;
    private SubTask selectedItem;
    RecyclerView listView;
    View view;
    SubTaskController subTaskController;
    Toolbar myToolbar;

    public SubTask getSelectedItem(){
        return selectedItem;
    }

    /**
     * mark subtask as complete
     * @param subTask the subtask chosen by user
     */
    private void markAsComplete(SubTask subTask) {
        subTask.setStatus(!subTask.getStatus());
        subTaskController.update(subTask);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);
        getViews();
        getControllers();
        setContent();
    }

    /**
     * get all view items to work with later
     */
    void getViews(){
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        listView = (RecyclerView)findViewById(R.id.task_detailed_view);
        view = findViewById(R.id.content);
    }

    /**
     * get all controllers to work with later
     */
    void getControllers(){
        subTaskController = new SubTaskController(this);
    }

    /**
     * set all content configuration and click listeners
     */
    void setContent(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        subTaskController.getAllByTask(true, getIntent().getIntExtra("id", 0));
        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                final SubTask simple = subTasks.get(pos);
                subTaskController.delete(simple);
                Snackbar.make(view, getString(R.string.deleted_string_firstpart)+" "+simple.getName()+" "+getString(R.string.deleted_string_secondpart), Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subTaskController.create(simple);
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
        listView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listView, new MainTasksActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                markAsComplete(subTasks.get(position));
            }
            @Override
            public void onLongClick(View view, int position) {
                AlertDialogFragment alertdFragment = new AlertDialogFragment();
                alertdFragment.show(fm, "Alert Dialog Fragment");
                selectedItem = subTasks.get(position);
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            selectedItem = null;
            AlertDialogFragment alertdFragment = new AlertDialogFragment();
            alertdFragment.show(fm, "Alert Dialog Fragment");
        }
        if (id == android.R.id.home)
            startActivity(new Intent(this, MainTasksActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public void showItems(List<SubTask> items){
        subTasks = items;
        final SubTaskAdapter mAdapter = new SubTaskAdapter(items);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void showTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
            actionBar.setTitle(title);
    }

    /**
     * AlertDialogFragment callback, creates a new subtask in the database or updates it, if needed
     * @param name name of the created subtask
     */
    public void itemCreatedCallback(String name) {
        if (selectedItem != null) {
            SubTask simple = selectedItem;
            simple.setName(name);
            simple.setStatus(false);
            subTaskController.update(simple);
        }
        else {
            subTaskController.create(name);
        }
    }
}
