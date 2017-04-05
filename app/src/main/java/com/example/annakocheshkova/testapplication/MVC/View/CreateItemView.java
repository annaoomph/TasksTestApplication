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
     * get date of the created or updated task
     * @return datepicker containing chosen date
     */
    DatePicker getDate();

    /**
     * get time of the created or updated task
     * @return timepicker containing chosen time
     */
    TimePicker getTime();

    /**
     * find out if alarm for newly created item needed
     * @return true if needed, false if not
     */
    boolean ifFireAlarm();
}
