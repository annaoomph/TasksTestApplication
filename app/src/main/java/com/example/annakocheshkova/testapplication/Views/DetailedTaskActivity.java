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

import com.example.annakocheshkova.testapplication.Controllers.AlarmController;
import com.example.annakocheshkova.testapplication.Controllers.TaskController;
import com.example.annakocheshkova.testapplication.Controllers.SubTaskController;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.Services.AlarmReceiver;


import java.util.List;

public class DetailedTaskActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    List<SubTask> subTasks;
    private SubTask selectedItem;
    RecyclerView listView;
    static Task mainTask;
    View view;
    TaskController taskController;
    SubTaskController subTaskController;
    AlarmController alarmController;
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
        taskController = new TaskController(this);
        subTaskController = new SubTaskController(this);
        alarmController = new AlarmController(this);
    }

    /**
     * set all content configuration and click listeners
     */
    void setContent(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        boolean from_notif = getIntent().getBooleanExtra("from_notif", false);
        if (from_notif) {
            int alarm_id = getIntent().getIntExtra("alarm_id", -1);
            if (alarm_id>=0) {
                AlarmReceiver.removeAlarm(alarm_id, getApplicationContext());
                alarmController.delete(alarm_id);
            }
        }
        mainTask = taskController.get(getIntent().getIntExtra("id", 0));
        subTaskController.getAllByTask(true, mainTask);
        getSupportActionBar().setTitle(mainTask.getName());
        listView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                final SubTask simple = subTasks.get(pos);
                subTaskController.delete(simple);
                Snackbar.make(view, R.string.deleted_string_firstpart+simple.getName()+R.string.deleted_string_secondpart, Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
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

    /**
     * AlertDialogFragment callback, creates a new subtask in the database or updates it, if needed
     * @param name name of the created subtask
     */
    public void itemCreatedCallback(String name) {
        SubTaskController scc = new SubTaskController(this);
        if (selectedItem != null) {
            SubTask simple = selectedItem;
            simple.setName(name);
            simple.setStatus(false);
            scc.update(simple);
        }
        else {
            SubTask simple = new SubTask(mainTask, name, false);
            scc.create(simple);
        }
    }
}
