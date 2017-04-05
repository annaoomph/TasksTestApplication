package com.example.annakocheshkova.testapplication.MVC.Controller;
import android.app.AlarmManager;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.CreateItemView;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Alarm.CustomAlarmManager;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
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
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        if (id>0) {
            Task task = dataStore.getTask(id);
            editingTask = task;
            AlarmInfo alarm = customAlarmManager.getByTaskId(task.getID());
            view.showItem(task, alarm);
        } else {
            editingTask = null;
        }
    }

    /**
     * event when user updates or creates a certain task;
     * method finds out if user was updating the item and creates or updates it depending on the result
     */
    public void onItemEditingFinished() {
        //TODO get alarm (before...)
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
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        long timeToSchedule = calendar.getTimeInMillis();
        Task task;
        if (editingTask != null) {
            task = editingTask;
            if (task.hasAlarms()) {
                int deletedAlarmId = customAlarmManager.get(task.getID()).getID();
                customAlarmManager.delete(deletedAlarmId);
                AlarmReceiver.removeAlarm(deletedAlarmId);
            }
            task.setName(name);
            task.setTime(timeToSchedule);
            dataStore.updateTask(task);
        } else {
            task = new Task(name, timeToSchedule);
            dataStore.createTask(task);
        }

        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, timeToSchedule);
            int alarmId = customAlarmManager.create(alarm);
            AlarmReceiver.addAlarm(task,timeToSchedule, alarmId );
        }
    }
}
