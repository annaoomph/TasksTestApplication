package com.example.annakocheshkova.testapplication.mvc.Controller;
import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.View.TaskView;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.utils.Listener.UndoListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A controller which handles all the actions connected with tasks
 */
public class TaskController implements UndoListener<Task> {

    /**
     * Datastore to work with data
     */
    private DataStore dataStore;

    /**
     * Main view
     */
    private TaskView view;

    /**
     * Creates an instance of task controller
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
            ReminderAlarmManager.addAlarm(deletedItem);
        }
        onViewLoaded();
    }

    /**
     * Compares two tasks for proper sorting
     * @param firstTask first task to be compared
     * @param secondTask second task to be compared
     * @return the result of the comparison (0 if they has equal place, -1 if the first is higher)
     */
    private int compareTasks(Task firstTask, Task secondTask) {
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

    /**
     * Sorts the given list of tasks
     * @param tasks list
     */
    private void sort (List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task firstTask, Task secondTask) {
                return compareTasks(firstTask, secondTask);
            }
        });
    }
}
