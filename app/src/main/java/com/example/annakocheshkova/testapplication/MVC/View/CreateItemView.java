package com.example.annakocheshkova.testapplication.MVC.View;

import com.example.annakocheshkova.testapplication.Model.Task;

/**
 * an interface for create item activity
 */
public interface CreateItemView {
    /**
     * shows the item to be edited
     * @param item item to be edited
     * @param alarmTime the chosen period
     */
    void showItem(Task item, long alarmTime);

    /**
     * close the view if the item was updated/created
     */
    void close();

    /**
     * shows an error when the wrong time is set
     */
    void showWrongTimeError();

    /**
     * shows an error when the wrong alarm time is set
     */
    void showWrongAlarmTimeError();

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

    /**
     * get the time when to fire alarm
     * @return the chosen period
     */
    long getReminderTime();
}
