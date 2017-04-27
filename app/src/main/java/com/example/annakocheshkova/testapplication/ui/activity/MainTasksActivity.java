package com.example.annakocheshkova.testapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.mvc.controller.TaskController;
import com.example.annakocheshkova.testapplication.ui.adapter.TaskAdapter;
import com.example.annakocheshkova.testapplication.mvc.view.TaskView;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.component.UndoComponent;

import java.util.List;

/**
 * A view of the main activity with list of all the tasks
 */
public class MainTasksActivity extends BaseActivity implements TaskView {

    /**
     * Adapter for the recycler view
     */
    TaskAdapter taskAdapter;

    /**
     * The main controller
     */
    TaskController taskController;

    /**
     * View with content
     */
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskController.onViewLoaded();
        checkLogin();
    }

    int getLayoutResId() {
        return R.layout.activity_tasks;
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
            startActivity(new Intent(this, CreateTaskActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean useDrawer() {
        return true;
    }

    /**
     * Sets all the content configuration and listeners
     */
    void setContent() {
        view = findViewById(R.id.main_content);
        RecyclerView listView = (RecyclerView)findViewById(R.id.tasks_view);
        taskController = new TaskController(this);
        taskAdapter = new TaskAdapter(taskController);
        listView.setAdapter(taskAdapter);
        taskController.onViewLoaded();
        listView.setHasFixedSize(true); //for better performance
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                Task item = taskAdapter.getItem(pos);
                taskController.onDelete(item);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listView);

        checkLogin();
    }

    @Override
    public void showItems(List<Task> items) {
        taskAdapter.setData(items);
    }

    @Override
    public void showCancelBar(Task task) {
        UndoComponent<Task> undoComponent = new UndoComponent<>();
        undoComponent.make(view, new Task(task), taskController, task.getName()); //make sure we are making the copy of the task to be deleted
    }

    @Override
    public void openTask(int id) {
        Intent intent = new Intent(view.getContext(), SubTaskActivity.class);
        intent.putExtra("id", id);
        view.getContext().startActivity(intent);
    }

    @Override
    public void editTask(int id) {
        Intent intent = new Intent(view.getContext(), CreateTaskActivity.class);
        intent.putExtra("id", id);
        view.getContext().startActivity(intent);
    }

    @Override
    public void showLoginScreen() {
        super.showLoginScreen();
    }

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.window_title);
    }

    private void checkLogin() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        boolean loggedIn = preferencesManager.getLoggedIn();
        showLoginButton(!loggedIn);
    }
}
