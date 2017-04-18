package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.TaskView;
import com.example.annakocheshkova.testapplication.operation.LoginOperation;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.example.annakocheshkova.testapplication.utils.listener.UndoListener;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A controller which handles all the actions connected with tasks
 */
public class TaskController implements UndoListener<Task> {

    /**
     * A datastore to work with data
     */
    private DataStore dataStore;

    /**
     * Main view
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
        List<Task> tasks = dataStore.getAllTasks();
        sort(tasks);
        view.showItems(tasks);
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        Converter<String> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
        LoginOperation loginOperation = new LoginOperation(url, converter);
        loginOperation.executeGet(new OperationListener<LoginOperation>() {
            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.setString(PreferencesManager.TOKEN, token);
                view.showLoginButton(false);
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                logout();
            }
        });
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
            if (deletedItem.hasAlarm()) {
                ReminderAlarmManager.addAlarm(deletedItem);
            }
        }
        onViewLoaded();
    }

    /**
     * Sorts the given list of tasks
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
        boolean loggedIn = preferencesManager.getBoolean(PreferencesManager.LOGGED_IN);
        if (loggedIn) {
            logout();
        } else {
            view.showLoginScreen();
        }
    }

    private void logout() {
        preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, false);
        preferencesManager.setString(PreferencesManager.TOKEN, "");
        view.showLoginButton(true);
    }
}
