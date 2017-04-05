package com.example.annakocheshkova.testapplication.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.annakocheshkova.testapplication.Model.Alarm.CustomAlarmManager;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import java.util.List;

/**
 * Listener receiving boot complete event, reschedules all the notifications after reboot
 */
public class MyBootReceiver extends BroadcastReceiver
{
    /**
     * called when reboot has happened
     * @param context current context
     * @param intent current intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        this.scheduleAlarms(context);
    }

    /**
     * this method reads all the alarms from the database and reschedules them
     * @param context application context
     */
    private void scheduleAlarms(Context context) {
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        List<AlarmInfo> alarms = customAlarmManager.getAll();
        for (AlarmInfo alarm : alarms) {
            long timeToSchedule = alarm.getTime();
            String name = alarm.getName();
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            alarmIntent.putExtra("name", name);
            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeToSchedule, alarmPendingIntent);
        }
    }
}