package com.example.annakocheshkova.testapplication.mvc.View;

import com.example.annakocheshkova.testapplication.model.Task;

/**
 * An interface for create item activity
 */
public interface CreateItemView {
    /**
     * Shows the item to be edited
     * @param item item to be edited
     * @param alarmTime the chosen period
     */
    void showItem(Task item, long alarmTime);

    /**
     * Closes the view if the item was updated/created
     */
    void close();

    /**
     * Shows an error when the wrong time is set
     */
    void showWrongTimeError();

    /**
     * Shows an error when the wrong alarm time is set
     */
    void showWrongAlarmTimeError();

    /**
     * Gets name of the created item or new name of the updated
     * @return name
     */
    String getName();

    /**
     * Gets year set by user
     * @return year
     */
    int getYear();

    /**
     * Gets month set by user
     * @return month
     */
    int getMonth();

    /**
     * Gets day set by user
     * @return day
     */
    int getDay();

    /**
     * Gets hour set by user
     * @return hour
     */
    int getHour();

    /**
     * Gets minute set by user
     * @return minute
     */
    int getMinute();

    /**
     * Finds out if alarm needs to be fired
     * @return true if needs, false if not
     */
    boolean ifFireAlarm();

    /**
     * Gets the time when to fire alarm
     * @return the chosen period
     */
    long getReminderTime();
}
