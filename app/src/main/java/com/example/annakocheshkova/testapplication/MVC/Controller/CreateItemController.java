package com.example.annakocheshkova.testapplication.MVC.Controller;
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
        if (id>0) {
            Task task = dataStore.getTask(id);
            editingTask = task;
            view.showItem(task, dataStore.getAllAlarmsByTaskId(task.getID()).get(0));
        } else {
            editingTask = null;
        }
    }

    /**
     * event when user updates or creates a certain task
     */
    public void onItemEditingFinished() {
        String name = view.getName();
        boolean fireAlarm = view.ifFireAlarm();
        DatePicker datePicker = view.getDate();
        TimePicker timePicker = view.getTime();
        int hour, minute;
        if(Build.VERSION.SDK_INT < 23) {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        } else {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), hour, minute);
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
            dataStore.updateTask(task);
        } else {
            task = new Task(name);
            dataStore.createTask(task);
        }

        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, timeToSchedule);
            int alarmId = customAlarmManager.create(alarm);
            AlarmReceiver.addAlarm(task,timeToSchedule, alarmId );
        }
    }
}
