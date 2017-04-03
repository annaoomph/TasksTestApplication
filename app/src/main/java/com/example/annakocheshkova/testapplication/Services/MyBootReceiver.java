package com.example.annakocheshkova.testapplication.Services;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.annakocheshkova.testapplication.Controllers.AlarmController;
import com.example.annakocheshkova.testapplication.Models.AlarmInfo;
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
        AlarmController ac = new AlarmController((Activity)ctxt);
        List<AlarmInfo> alarms = ac.getAll();
        for (int i=0; i<alarms.size(); i++)
        {
            long mms = alarms.get(i).getTime();
            String name = alarms.get(i).getName();
            int interval = alarms.get(i).getInterval();
            Intent alarmIntent = new Intent(ctxt, AlarmReceiver.class);
            alarmIntent.putExtra("name", name);
            PendingIntent mAlarmSender = PendingIntent.getBroadcast(ctxt, 0, alarmIntent, 0);
            AlarmManager am = (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
            if (interval<0)
                am.set(AlarmManager.RTC_WAKEUP, mms, mAlarmSender);
            else am.setRepeating(AlarmManager.RTC_WAKEUP, mms, interval, mAlarmSender);
        }
    }
}