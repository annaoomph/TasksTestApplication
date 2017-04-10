package com.example.annakocheshkova.testapplication.MVC.Controller;
import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Alarm.CustomAlarmManager;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MVC.View.TaskView;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import com.example.annakocheshkova.testapplication.Utils.Listener.UndoListener;

import java.util.ArrayList;
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
     * constructor
     * @param view main view
     */
    public TaskController(TaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * event called everytime you need to update the main view list of tasks
     */
    public void onViewLoaded() {
        List<Task> tasks = dataStore.getAllTasks();
        sort(tasks);
        view.showItems(tasks);
    }

    /**
     * event when some task is chosen to be edited
     * @param id id of the chosen task
     */
    public void onItemUpdate(int id) {
        view.editTask(id);
    }

    /**
     * event when some task is chosen to be opened
     * @param id id of the chosen task
     */
    public void onItemChosen(int id) {
        view.openTask(id);
    }


    /**
     * event called when an item needs to be deleted
     * @param item task to be deleted
     */
    public void onDelete(Task item) {
        view.showCancelBar(item);
        dataStore.deleteTask(item);
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        customAlarmManager.deleteByTaskId(item.getID());
        dataStore.deleteSubTasksByTask(item);
        onViewLoaded();
    }

    @Override
    public void onUndo(Task item) {
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        Task deletedItem = (Task)item;
        if (deletedItem != null) {
            Task task = new Task(deletedItem.getName(), deletedItem.getTime());
            dataStore.createTask(task);
            for (SubTask subTask : deletedItem.getSubTasks()) {
                subTask.setTask(task);
                dataStore.createSubTask(subTask);
            }
            for (AlarmInfo alarm : deletedItem.getAlarms()) {
                alarm.setTask(task);
                customAlarmManager.create(alarm);
                AlarmReceiver.addAlarm(deletedItem, alarm.getTime(), alarm.getID());
            }
        }
        onViewLoaded();
    }

    /**
     * a method to compare two tasks for proper sorting
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
     * sorts the given list of tasks
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
