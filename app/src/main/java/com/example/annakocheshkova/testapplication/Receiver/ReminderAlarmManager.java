package com.example.annakocheshkova.testapplication.Receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Activity.SubTaskActivity;

import java.util.List;

/**
 * Manager responsible for scheduling alarms, removing them and receiving them
 */
public class ReminderAlarmManager extends BroadcastReceiver {

    /**
     * Called when the time has come for notification to be fired
     * @param context current application context
     * @param intent current intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent.getStringExtra("name"), intent.getIntExtra("id", 0));
    }

    /**
     * Shows notification for a certain task
     * @param context current context
     * @param name name of the task
     * @param id id of the task
     */
    public static void showNotification(Context context, String name, int id) {
        onAlarmFired(id);
        Intent alarmIntent = new Intent(context, SubTaskActivity.class);
        alarmIntent.putExtra("id", id);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setTicker(context.getString(R.string.attention));
        notificationBuilder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        notificationBuilder.setContentTitle(context.getString(R.string.notification_title));
        notificationBuilder.setContentText(name);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setVibrate(new long[] {200, 100, 100, 100});
        notificationBuilder.setAutoCancel(true);
        Notification notification = notificationBuilder.build();
        notificationManager.notify(101, notification);
    }

    /**
     * Creates a new alarm
     * @param newTask task for the alarm
     */
    public static void addAlarm(Task newTask) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, ReminderAlarmManager.class);
        alarmIntent.putExtra("name", newTask.getName());
        alarmIntent.putExtra("id", newTask.getID());
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, newTask.getID(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,  newTask.getAlarmTime(), alarmPendingIntent);
    }

    /**
     * Disables a certain alarm
     * @param task task from which the alarm should be deleted
     */
    public static void removeAlarm(Task task) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, ReminderAlarmManager.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, task.getID(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmPendingIntent);
    }

    /**
     * Reads all the alarms from the database and reschedules them
     */
    public static void scheduleAlarms() {
        DataStore dataStore = DataStoreFactory.getDataStore();
        List<Task> tasksWithAlarms = dataStore.getAllTasksWithAlarms();
        for (Task task : tasksWithAlarms) {
            addAlarm(task);
        }
    }

    /**
     * Called when the notification was shown
     * @param id id of the task which notification was shown
     */
    public static void onAlarmFired(int id) {
        DataStore dataStore = DataStoreFactory.getDataStore();
        Task task = dataStore.getTask(id);
        task.setNotification(false);
        dataStore.updateTask(task);
    }
}