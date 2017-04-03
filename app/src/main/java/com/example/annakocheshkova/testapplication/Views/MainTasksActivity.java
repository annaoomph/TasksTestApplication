package com.example.annakocheshkova.testapplication.Views;

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
import com.example.annakocheshkova.testapplication.Controllers.TaskController;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.R;
import java.util.List;

/**
 * a view of the main activity with list of all the tasks
 */
public class MainTasksActivity extends AppCompatActivity implements TaskView{

    List<Task> taskCategories; //list of all tasks
    ActionBarDrawerToggle drawerToggle;
    String[] leftDrawerTitles; //left menu list
    ListView drawerListView;
    DrawerLayout drawerLayout;
    RecyclerView listView;
    View view;
    Toolbar toolbar;
    TaskController taskController;

    interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        getViews();
        getControllers();
        setContent();
    }

    void getControllers() {
        taskController = new TaskController(this);
        taskController.getAll();
    }

    void getViews(){
        leftDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (RecyclerView)findViewById(R.id.tasks_view);
        view = findViewById(R.id.main_content);
    }

    void setContent(){
        drawerListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, leftDrawerTitles));
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                                                      drawerLayout.closeDrawer(drawerListView);
                                                  }
                                              }
        );
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null)
            actionBar.setTitle(R.string.window_title);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_navigation_drawer,
                R.string.close_navigation_drawer) {

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
                Task simple = taskCategories.get(pos);
                taskController.delete(simple);

                Snackbar.make(view,  getString(R.string.deleted_string_firstpart)+" " + simple.getName()+" "+getString(R.string.deleted_string_secondpart), Snackbar.LENGTH_LONG).setAction(R.string.cancel_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskController.restoreDeleted();
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
        listView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailedTaskActivity.class);
                intent.putExtra("id", taskCategories.get(position).getID());
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), CreateItemActivity.class);
                intent.putExtra("id", taskCategories.get(position).getID());
                startActivity(intent);
            }
        }));
    }

    public void showItems(List<Task> items) {
        taskCategories = items;
        TaskAdapter mAdapter = new TaskAdapter(taskCategories);
        listView.setAdapter(mAdapter);
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
}
