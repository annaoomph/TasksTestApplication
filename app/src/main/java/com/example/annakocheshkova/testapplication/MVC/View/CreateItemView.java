package com.example.annakocheshkova.testapplication.MVC.View;

import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Task;

/**
 * an interface for create item activity
 */
public interface CreateItemView {
    /**
     * shows the item to be edited
     * @param item item to be edited
     * @param alarm alarm of the task to be edited
     */
    void showItem(Task item, AlarmInfo alarm);

    /**
     * get name of the created item or new name of the updated
     * @return name
     */
    String getName();

    /**
     * get year set by user
     * @return year
     */
    int getYear();

    /**
     * get month set by user
     * @return month
     */
    int getMonth();

    /**
     * get day set by user
     * @return day
     */
    int getDay();

    /**
     * get hour set by user
     * @return hour
     */
    int getHour();

    /**
     * get minute set by user
     * @return minute
     */
    int getMinute();


    /**
     * find out if alarm needs to be fired
     * @return true if needs, false if not
     */
    boolean ifFireAlarm();
}
