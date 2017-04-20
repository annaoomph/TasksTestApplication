package com.example.annakocheshkova.testapplication.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.mvc.controller.TaskController;
import com.example.annakocheshkova.testapplication.ui.adapter.TaskAdapter;
import com.example.annakocheshkova.testapplication.mvc.view.TaskView;
import com.example.annakocheshkova.testapplication.mvc.model.Task;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.component.UndoComponent;

import java.util.List;

/**
 * A view of the main activity with list of all the tasks
 */
public class MainTasksActivity extends AppCompatActivity implements TaskView {

    /**
     * Adapter for the recycler view
     */
    TaskAdapter taskAdapter;

    /**
     * Button which toggles the drawer (open/close)
     */
    ActionBarDrawerToggle drawerToggle;

    /**
     * Main view with all the contents
     */
    View view;

    /**
     * The main controller
     */
    TaskController taskController;

    /**
     * A left-side menu
     */
    ListView drawerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        setContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskController.onViewLoaded();
        checkLogin();
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Sets all the content configuration and listeners
     */
    void setContent() {
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        RecyclerView listView = (RecyclerView)findViewById(R.id.tasks_view);
        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        view = findViewById(R.id.main_content);
        taskController = new TaskController(this);
        taskAdapter = new TaskAdapter(taskController);
        listView.setAdapter(taskAdapter);
        taskController.onViewLoaded();
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view, int position, long id){
                drawerLayout.closeDrawer(drawerListView);
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(view.getContext(), ImportActivity.class);
                        view.getContext().startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(view.getContext(), ExportActivity.class);
                        view.getContext().startActivity(intent);
                        break;
                    }
                    case 2: {
                        taskController.onLoginClicked();
                        break;
                    }
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.window_title);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
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
    public void showLoginButton(boolean show) {
        String[] leftDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        if (!show) {
            leftDrawerTitles = getResources().getStringArray(R.array.drawer_items_for_logged_in);
        }
        drawerListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, leftDrawerTitles));
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        view.getContext().startActivity(intent);
    }

    private void checkLogin() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        boolean loggedIn = preferencesManager.getBoolean(PreferencesManager.LOGGED_IN);
        showLoginButton(!loggedIn);
    }
}
