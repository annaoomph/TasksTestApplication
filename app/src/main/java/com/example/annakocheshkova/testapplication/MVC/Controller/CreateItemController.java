package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.CreateItemView;
import com.example.annakocheshkova.testapplication.Utils.NotificationAlarmManager;
import com.example.annakocheshkova.testapplication.Model.Task;

import java.util.Calendar;

/**
 * controller for create item view
 */
public class CreateItemController {

    /**
     * datastore example to work with data
     */
    private DataStore dataStore;

    /**
     * main view of this controller
     */
    private CreateItemView view;

    /**
     * task we are currently editing (null if creating)
     */
    private Task editingTask;

    /**
     * constructor. creates an example of the datastore
     * @param view main view
     */
    public CreateItemController(CreateItemView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * event called everytime you need to update view (show information about task)
     * @param id id of the task to be shown (-1 if no need to show)
     */
    public void onViewLoaded(int id) {
        if (id>0) {
            Task task = dataStore.getTask(id);
            editingTask = task;
            view.showItem(task);
        } else {
            editingTask = null;
        }
    }

    /**
     * event when user updates or creates a certain task;
     * method finds out if user was updating the item and creates or updates it depending on the result
     */
    public void onItemEditingFinished() {
        String name = view.getName();
        boolean fireAlarm = view.ifFireAlarm();
        int year = view.getYear();
        int month = view.getMonth();
        int day = view.getDay();
        int hour = view.getHour();
        int minute = view.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day, hour, minute);
        long timeToSchedule = calendar.getTimeInMillis();
        //TODO This is different
        long timeForAlarm = calendar.getTimeInMillis();
        Task task;
        if (editingTask != null) {
            task = editingTask;
            if (task.hasAlarms()) {
                task.onAlarmCancelled();
                NotificationAlarmManager.removeAlarm(task);
            }
            if (fireAlarm)
                task.setNotification(timeForAlarm);
            task.setName(name);
            task.setTime(timeToSchedule);
            dataStore.updateTask(task);
        } else {
            task = new Task(name, timeToSchedule);
            if (fireAlarm)
                task.setNotification(timeForAlarm);
            dataStore.createTask(task);
        }

        if (fireAlarm) {
            NotificationAlarmManager.addAlarm(task);
        }
    }
}
