package com.example.annakocheshkova.testapplication.Services;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.example.annakocheshkova.testapplication.Controllers.AlarmController;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.Views.DetailedTaskActivity;

/**
 * listener responding to scheduled alarms, shows notifications when it is needed, creates or disables them
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        runNotification((Activity)context, intent.getStringExtra("name"), intent.getIntExtra("id", 0),intent.getIntExtra("alarm_id", 0),intent.getBooleanExtra("repeating", false));
    }

    /**
     * this method is responsible for showing notification for a certain task
     * @param activity activity
     * @param name name of the task
     * @param id id of the task
     * @param alarm_id id of the alarm
     * @param repeating if it is repeating
     */
    public static void runNotification(Activity activity, String name, int id, int alarm_id, boolean repeating) {
        AlarmController ac = new AlarmController(activity);
        Intent alarmIntent = new Intent(activity, DetailedTaskActivity.class);
        if (!repeating)
            ac.delete(alarm_id);
        else {
            alarmIntent.putExtra("alarm_id", alarm_id);
            alarmIntent.putExtra("from_notif", true);
        }
        alarmIntent.putExtra("id", id);
        NotificationManager nm = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(activity, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
        builder.setTicker("Attention");
        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        builder.setContentTitle(activity.getString(R.string.notification_title));
        builder.setContentText(name);
        builder.setContentIntent(p);
        builder.setVibrate(new long[]{200, 100, 100, 100});
        Notification n = builder.build();
        nm.notify(101, n);
    }

    /**
     * create a new alarm
     * @param activity activity
     * @param newTask task for the alarm
     * @param intervalDuration (to be deleted)
     * @param timeToSchedule time in which the notification should be run
     * @param alarm_id id of the alarm in the database
     */
    public static void addAlarm(Activity activity, Task newTask, int intervalDuration, long timeToSchedule, int alarm_id){
        AlarmController ac = new AlarmController(activity);
        Intent alarmIntent = new Intent(activity, AlarmReceiver.class);
        alarmIntent.putExtra("name", newTask.getName());
        alarmIntent.putExtra("id", newTask.getID());
        alarmIntent.putExtra("alarm_id", alarm_id);
        if (intervalDuration < 0)
            alarmIntent.putExtra("repeating", false);
        else alarmIntent.putExtra("repeating", true);
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(activity, ac.getAlarmId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        if (intervalDuration < 0)
            am.set(AlarmManager.RTC_WAKEUP, timeToSchedule, mAlarmSender);
        else am.setRepeating(AlarmManager.RTC_WAKEUP, timeToSchedule, intervalDuration, mAlarmSender);
    }

    /**
     * disable a certain alarm
     * @param id id of the alarm
     * @param context application context
     */
    public static void removeAlarm(int id, Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(mAlarmSender);
    }
}