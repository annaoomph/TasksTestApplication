package com.example.annakocheshkova.testapplication.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.example.annakocheshkova.testapplication.Utils.NotificationAlarmManager;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Activity.SubTaskActivity;

/**
 * listener responding to scheduled alarms, shows notifications when it is needed, creates or disables them
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * when the time has come for notification to be fired
     * @param context current application context
     * @param intent current intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        runNotification(context, intent.getStringExtra("name"), intent.getIntExtra("id", 0));
    }

    /**
     * this method is responsible for showing notification for a certain task
     * @param context current context
     * @param name name of the task
     * @param id id of the task
     */
    public static void runNotification(Context context, String name, int id) {
        NotificationAlarmManager.onAlarmFired(id);
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
        Notification notification = notificationBuilder.build();
        notificationManager.notify(101, notification);
    }
}