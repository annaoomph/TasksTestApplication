package com.example.annakocheshkova.testapplication.mvc.controller;
import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.TaskView;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.utils.Listener.UndoListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with tasks
 */
public class TaskController implements UndoListener<Task> {

    /**
     * datastore to work with data
     */
    private DataStore dataStore;

    /**
     * main view
     */
    private TaskView view;

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
        //TODO method try to login
        List<Task> tasks = dataStore.getAllTasks();
        sort(tasks);
        view.showItems(tasks);
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
}
