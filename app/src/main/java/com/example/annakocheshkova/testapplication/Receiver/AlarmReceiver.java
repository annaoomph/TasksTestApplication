package com.example.annakocheshkova.testapplication.Receiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmManager;
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
     * @param
     * @param name name of the task
     * @param id id of the task
     * @param alarm_id id of the alarm
     */
    public static void runNotification(Context context, String name, int id, int alarm_id) {
        AlarmManager ac = new AlarmManager();

        Intent alarmIntent = new Intent(context, DetailedTaskActivity.class);
        ac.delete(alarm_id);
        alarmIntent.putExtra("id", id);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Attention");
        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        builder.setContentTitle(context.getString(R.string.notification_title));
        builder.setContentText(name);
        builder.setContentIntent(p);
        builder.setVibrate(new long[]{200, 100, 100, 100});
        Notification n = builder.build();
        nm.notify(101, n);
    }

    /**
     * create a new alarm
     * @param newTask task for the alarm
     * @param timeToSchedule time in which the notification should be run
     * @param alarm_id id of the alarm in the database
     */
    public static void addAlarm(Task newTask, long timeToSchedule, int alarm_id){
        Context context = MyApplication.getAppContext();
        AlarmManager ac = new AlarmManager();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("name", newTask.getName());
        alarmIntent.putExtra("id", newTask.getID());
        alarmIntent.putExtra("alarm_id", alarm_id);
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, ac.getAlarmId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        android.app.AlarmManager am = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(android.app.AlarmManager.RTC_WAKEUP, timeToSchedule, mAlarmSender);
    }

    /**
     * disable a certain alarm
     * @param id id of the alarm
     */
    public static void removeAlarm(int id) {
        Context context = MyApplication.getAppContext();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        android.app.AlarmManager am = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(mAlarmSender);
    }
}