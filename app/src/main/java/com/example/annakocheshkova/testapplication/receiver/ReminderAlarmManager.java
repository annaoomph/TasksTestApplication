package com.example.annakocheshkova.testapplication.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.ui.activity.SubTaskActivity;

import java.util.List;

/**
 * listener responding to scheduled alarms, shows notifications when it is needed, creates or disables them
 */
public class ReminderAlarmManager extends BroadcastReceiver {

    /**
     * when the time has come for notification to be fired
     * @param context current application context
     * @param intent current intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent.getStringExtra("name"), intent.getIntExtra("id", 0));
    }

    /**
     * this method is responsible for showing notification for a certain task
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
     * create a new alarm
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
     * disable a certain alarm
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
        task.setNotification(false);
        dataStore.updateTask(task);
    }
}