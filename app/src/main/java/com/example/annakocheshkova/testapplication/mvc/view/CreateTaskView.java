package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.model.Task;

/**
 * an interface for create item activity
 */
public interface CreateTaskView {
    /**
     * shows the item to be edited
     * @param item item to be edited
     * @param alarmTime the chosen period
     */
    void showItem(Task item, long alarmTime);

    /**
     * closes the view if the item was updated/created
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
     * gets name of the created item or new name of the updated
     * @return name
     */
    String getName();

    /**
     * gets year set by user
     * @return year
     */
    int getYear();

    /**
     * gets month set by user
     * @return month
     */
    int getMonth();

    /**
     * gets day set by user
     * @return day
     */
    int getDay();

    /**
     * gets hour set by user
     * @return hour
     */
    int getHour();

    /**
     * gets minute set by user
     * @return minute
     */
    int getMinute();


    /**
     * finds out if alarm needs to be fired
     * @return true if needs, false if not
     */
    boolean ifFireAlarm();

    /**
     * gets the time when to fire alarm
     * @return the chosen period
     */
    long getReminderTime();
}
