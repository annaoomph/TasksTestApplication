package com.example.annakocheshkova.testapplication.UI.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
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
import com.example.annakocheshkova.testapplication.MVC.Controller.TaskController;
import com.example.annakocheshkova.testapplication.UI.Adapter.TaskAdapter;
import com.example.annakocheshkova.testapplication.MVC.View.TaskView;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * a view of the main activity with list of all the tasks
 */
public class MainTasksActivity extends AppCompatActivity implements TaskView {

    /**
     * adapter for the recycler view
     */
    TaskAdapter taskAdapter;

    /**
     * button which toggles the drawer (open/close)
     */
    ActionBarDrawerToggle drawerToggle;

    /**
     * menu for the drawer
     */
    String[] leftDrawerTitles;

    /**
     * listview for the drawer
     */
    ListView drawerListView;

    /**
     * the drawer itself
     */
    DrawerLayout drawerLayout;

    /**
     * listview with all the tasks
     */
    RecyclerView listView;

    /**
     * main view (needed for snackbar)
     */
    View view;

    /**
     * toolbar with menu
     */
    Toolbar toolbar;

    /**
     * the main controller
     */
    TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getViews();
        setContent();
    }

    /**
     * get all the views to work with later
     */
    void getViews() {
        leftDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (RecyclerView)findViewById(R.id.tasks_view);
        view = findViewById(R.id.main_content);
        List<Task> tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasks);
        listView.setAdapter(taskAdapter);
    }

    /**
     * set all the content configuration and listeners
     */
    void setContent() {
        taskController = new TaskController(this);
        taskController.onViewLoaded();
        drawerListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, leftDrawerTitles));
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                drawerLayout.closeDrawer(drawerListView);
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(R.string.window_title);

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
        listView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int pos = viewHolder.getAdapterPosition();
                taskController.onDelete(pos);
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
    public void showItems(List<Task> items) {
        taskAdapter.changeData(items);
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
            startActivity(new Intent(this, CreateItemActivity.class));
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

    @Override
    public void showCancelBar(String taskName) {
        Snackbar.make(view,  getString(R.string.deleted_string_firstpart)+" " + taskName+" "+getString(R.string.deleted_string_secondpart), Snackbar.LENGTH_LONG).setAction(R.string.cancel_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskController.onRestoreDeleted();
            }
        }).show();
    }
}
