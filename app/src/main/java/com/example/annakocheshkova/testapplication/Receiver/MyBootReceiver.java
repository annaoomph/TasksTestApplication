package com.example.annakocheshkova.testapplication.Receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmManager;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import java.util.List;

/**
 * Listener receiving boot complete event, reschedules all the notifications after reboot
 */
public class MyBootReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent) {
        this.scheduleAlarms(context);
    }

    /**
     * this method reads all the alarms from the database and reschedules them
     * @param ctxt application context
     */
    private void scheduleAlarms(Context ctxt) {
        AlarmManager ac = new AlarmManager();
        List<AlarmInfo> alarms = ac.getAll();
        for (int i=0; i<alarms.size(); i++)
        {
            long mms = alarms.get(i).getTime();
            String name = alarms.get(i).getName();
            Intent alarmIntent = new Intent(ctxt, AlarmReceiver.class);
            alarmIntent.putExtra("name", name);
            PendingIntent mAlarmSender = PendingIntent.getBroadcast(ctxt, 0, alarmIntent, 0);
            android.app.AlarmManager am = (android.app.AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
            am.set(android.app.AlarmManager.RTC_WAKEUP, mms, mAlarmSender);
        }
    }
}