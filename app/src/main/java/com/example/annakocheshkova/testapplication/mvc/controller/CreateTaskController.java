package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.mvc.view.CreateTaskView;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;

import java.util.Calendar;

/**
 * controller for create item view
 */
public class CreateTaskController {

    /**
     * datastore example to work with data
     */
    private DataStore dataStore;

    /**
     * main view of this controller
     */
    private CreateTaskView view;

    /**
     * task we are currently editing (null if creating)
     */
    private Task editingTask;

    /**
     * creates an example of the datastore
     * @param view main view
     */
    public CreateTaskController(CreateTaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * called everytime you need to update view (show information about task)
     * @param id id of the task to be shown (-1 if no need to show)
     */
    public void onViewLoaded(int id) {
        if (id>0) {
            Task task = dataStore.getTask(id);
            editingTask = task;
            long timePeriod = task.getTime() - task.getAlarmTime();
            view.showItem(task, timePeriod);
        } else {
            editingTask = null;
        }
    }

    /**
     * updates or creates a certain task;
     * finds out if user was updating the item and creates or updates it depending on the result
     */
    public void onItemEditingFinished() {
        String name = view.getName();
        boolean fireAlarm = view.ifFireAlarm();
        int year = view.getYear();
        int month = view.getMonth();
        int day = view.getDay();
        int hour = view.getHour();
        int minute = view.getMinute();
        long timePeriod = view.getReminderTime();
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        calendar.clear();
        calendar.set(year, month, day, hour, minute);
        long timeToSchedule = calendar.getTimeInMillis();
        long timeForAlarm = timeToSchedule - timePeriod;
        boolean correctTime = timeToSchedule > currentTime;
        boolean correctAlarmTime = timeForAlarm > currentTime;
        if (!correctTime) {
            view.showWrongTimeError();
        }
        else if (!correctAlarmTime) {
            view.showWrongAlarmTimeError();
        } else {
            Task task;
            if (editingTask != null) {
                task = editingTask;
                if (task.hasAlarms()) {
                    task.setNotification(false);
                    ReminderAlarmManager.removeAlarm(task);
                }
                if (fireAlarm) {
                    task.setNotification(true);
                    task.setNotificationTime(timeForAlarm);
                }
                task.setName(name);
                task.setTime(timeToSchedule);
                dataStore.updateTask(task);
            } else {
                task = new Task(name, timeToSchedule);
                if (fireAlarm) {
                    task.setNotification(true);
                    task.setNotificationTime(timeForAlarm);
                }
                dataStore.createTask(task);
            }

            if (fireAlarm) {
                ReminderAlarmManager.addAlarm(task);
            }
            view.close();
        }
    }
}
