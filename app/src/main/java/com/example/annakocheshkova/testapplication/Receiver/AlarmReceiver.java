package com.example.annakocheshkova.testapplication.Receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.example.annakocheshkova.testapplication.Model.Alarm.CustomAlarmManager;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Activity.DetailedTaskActivity;

/**
 * listener responding to scheduled alarms, shows notifications when it is needed, creates or disables them
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        runNotification(context, intent.getStringExtra("name"), intent.getIntExtra("id", 0),intent.getIntExtra("alarm_id", 0));
    }

    /**
     * this method is responsible for showing notification for a certain task
     * @param context current context
     * @param name name of the task
     * @param id id of the task
     * @param alarmId id of the alarm
     */
    public static void runNotification(Context context, String name, int id, int alarmId) {
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        customAlarmManager.delete(alarmId);
        Intent alarmIntent = new Intent(context, DetailedTaskActivity.class);
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
        Notification notification = notificationBuilder.build();
        notificationManager.notify(101, notification);
    }

    /**
     * create a new alarm
     * @param newTask task for the alarm
     * @param timeToSchedule time in which the notification should be run
     * @param alarmId id of the alarm in the database
     */
    public static void addAlarm(Task newTask, long timeToSchedule, int alarmId) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("name", newTask.getName());
        alarmIntent.putExtra("id", newTask.getID());
        alarmIntent.putExtra("alarm_id", alarmId);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeToSchedule, alarmPendingIntent);
    }

    /**
     * disable a certain alarm
     * @param id id of the alarm
     */
    public static void removeAlarm(int id) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmPendingIntent);
    }
}