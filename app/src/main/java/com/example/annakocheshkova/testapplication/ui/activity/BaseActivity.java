package com.example.annakocheshkova.testapplication.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.manager.LoginManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;

/**
 * A basic activity which sets up toolbar and drawer menu, if needed.
 * Contains some basic functionality for all the activities.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Button which toggles the drawer (open/close)
     */
    ActionBarDrawerToggle drawerToggle;

    /**
     * A left-side menu
     */
    ListView drawerListView;

    /**
     * Toolbar view
     */
    Toolbar toolbar;

    /**
     * Sets all the configuration and listeners
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (useToolbar()) {
            initToolbar();
        } else {
            toolbar.setVisibility(View.GONE);
        }
        if (useDrawer()) {
            initDrawer();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        RelativeLayout fullLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (useDrawer()) {
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (useDrawer()) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }

    /**
     * Gets the inner layout resource id
     * @return resource id
     */
    abstract int getLayoutResId();


    /**
     * Initializes the toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getToolBarTitle());
        }
        if (!useDrawer()) {
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Initializes the drawer
     */
    private void initDrawer() {
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
                        boolean loggedIn = preferencesManager.getLoggedIn();
                        if (loggedIn) {
                            logout();
                        } else {
                            showLoginScreen();
                        }
                        break;
                    }
                }
            }
        });
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
        showLoginButton(true);
    }

    /**
     * Gets the toolbar title if a certain activity
     * @return toolbar title
     */
    abstract protected String getToolBarTitle();

    /**
     * Defines whether use drawer menu or not; false by default, override if you want to have drawer
     * @return true if use drawer
     */
    protected boolean useDrawer() {
        return false;
    }

    /**
     * Defines whether use toolbar or not; true by default, override if you want to hide toolbar;
     * @return true if use drawer
     */
    protected boolean useToolbar() {
        return true;
    }

    /**
     * Shows or hides the login button
     * @param show true if show login, false if show logout
     */
    public void showLoginButton(boolean show) {
        String[] leftDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        if (!show) {
            leftDrawerTitles = getResources().getStringArray(R.array.drawer_items_for_logged_in);
        }
        drawerListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, leftDrawerTitles));
    }

    /**
     * Opens login screen
     */
    void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Completes logout
     */
    private void logout() {
        LoginManager.getInstance().logout();
        showLoginButton(true);
    }

    /**
     * Shows a toast
     * @param text text to be shown
     */
    void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
