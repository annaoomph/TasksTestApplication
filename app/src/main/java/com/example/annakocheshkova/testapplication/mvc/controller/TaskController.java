package com.example.annakocheshkova.testapplication.mvc.controller;
import android.content.Context;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.TaskView;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.utils.ConfigurationManager;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;
import com.example.annakocheshkova.testapplication.utils.Listener.UndoListener;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesManager;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with tasks
 */
public class TaskController implements UndoListener<Task>, HttpListener {

    /**
     * datastore to work with data
     */
    private DataStore dataStore;

    /**
     * main view
     */
    private TaskView view;

    /**
     * A manager for application preferences
     */
    private PreferencesManager preferencesManager;

    /**
     * Creates new instance of TaskController
     * @param view main view
     */
    public TaskController(TaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * Called everytime you need to update the main view list of tasks
     */
    public void onViewLoaded() {
        preferencesManager = PreferencesFactory.getPreferencesManager();
        Context context = MyApplication.getAppContext();
        List<Task> tasks = dataStore.getAllTasks();
        sort(tasks);
        view.showItems(tasks);
        HttpClient httpClient = new HttpClient(this);
        try {
            String url = ConfigurationManager.getConfigValue(MyApplication.getAppContext().getString(R.string.server_url_config_name));
            String fakeRequestString = ConfigurationManager.getConfigValue(MyApplication.getAppContext().getString(R.string.fake_request_config_name));
            if (fakeRequestString == null) {
                throw new IOException();
            }
            boolean fakeRequest = fakeRequestString.equalsIgnoreCase("true");
            if (fakeRequest) {
                httpClient.doFakeRequest(url);
            } else {
                httpClient.doGetRequest(url);
            }
        } catch (IOException e) {
            preferencesManager.setBoolean(context.getString(R.string.loggedIn_pref_name), false);
        }
    }

    /**
     * Called when some task is chosen to be edited
     * @param id id of the chosen task
     */
    public void onItemUpdate(int id) {
        view.editTask(id);
    }

    /**
     * Called when some task is chosen to be opened
     * @param id id of the chosen task
     */
    public void onItemChosen(int id) {
        view.openTask(id);
    }


    /**
     * Called when an item needs to be deleted
     * @param item task to be deleted
     */
    public void onDelete(Task item) {
        view.showCancelBar(item);
        ReminderAlarmManager.removeAlarm(item);
        dataStore.deleteTask(item);
        dataStore.deleteSubTasksByTask(item);
        onViewLoaded();
    }

    @Override
    public void onUndo(Task deletedItem) {
        if (deletedItem != null) {
            dataStore.createTask(deletedItem);
            for (SubTask subTask : deletedItem.getSubTasks()) {
                subTask.setTask(deletedItem);
                dataStore.createSubTask(subTask);
            }
            if (deletedItem.hasAlarm())
                ReminderAlarmManager.addAlarm(deletedItem);
        }
        onViewLoaded();
    }

    /**
     * sorts the given list of tasks
     * @param tasks list
     */
    private void sort (List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task firstTask, Task secondTask) {
                if (firstTask.getStatus().compareTo(secondTask.getStatus()) == 0) {
                    if (firstTask.getTime() > secondTask.getTime()) {
                        return 1;
                    } else if (firstTask.getTime() < secondTask.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else {
                    return firstTask.getStatus().compareTo(secondTask.getStatus());
                }
            }
        });
    }

    /**
     * Called when user pressed login/logout button
     */
    public void onLoginClicked() {
        boolean loggedIn = preferencesManager.getBoolean(MyApplication.getAppContext().getString(R.string.loggedIn_pref_name));
        if (loggedIn) {
            logout();
        } else {
            view.showLoginScreen();
        }
    }

    @Override
    public void onSuccess(String response) {
        Context context = MyApplication.getAppContext();
        preferencesManager.setBoolean(context.getString(R.string.loggedIn_pref_name), true);
        preferencesManager.setString(context.getString(R.string.token_pref_name), response);
        view.showLoginButton(false);
    }

    @Override
    public void onFailure() {
        logout();
    }

    @Override
    public void onUnauthorized() {
        logout();
    }

    private void logout() {
        Context context = MyApplication.getAppContext();
        preferencesManager.setBoolean(context.getString(R.string.loggedIn_pref_name), false);
        preferencesManager.setString(context.getString(R.string.token_pref_name), "");
        view.showLoginButton(true);
    }
}
