package com.example.annakocheshkova.testapplication.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import java.util.List;

/**
 * a manager which handles all the actions connected with alarms
 */
public class NotificationAlarmManager {

    /**
     * create a new alarm
     * @param newTask task for the alarm
     */
    public static void addAlarm(Task newTask) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("name", newTask.getName());
        alarmIntent.putExtra("id", newTask.getID());
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, newTask.getID(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,  newTask.getAlarmTime(), alarmPendingIntent);
    }

    /**
     * disable a certain alarm
     * @param task task from which the alarm should be deleted
     */
    public static void removeAlarm(Task task) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, task.getID(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmPendingIntent);
    }

    /**
     * this method reads all the alarms from the database and reschedules them
     */
    public static void scheduleAlarms() {
        DataStore dataStore = DataStoreFactory.getDataStore();
        List<Task> tasksWithAlarms = dataStore.getAllTasksWithAlarms();
        for (Task task : tasksWithAlarms) {
            addAlarm(task);
        }
    }

    /**
     * called when the notification was shown
     * @param id id of the task which notification was shown
     */
    public static void onAlarmFired(int id) {
        DataStore dataStore = DataStoreFactory.getDataStore();
        Task task = dataStore.getTask(id);
        task.onAlarmCancelled();
        dataStore.updateTask(task);
    }
}
