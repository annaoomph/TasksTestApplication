package com.example.annakocheshkova.testapplication.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.annakocheshkova.testapplication.Utils.NotificationAlarmManager;

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
        NotificationAlarmManager.scheduleAlarms();
    }
}