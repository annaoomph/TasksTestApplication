package com.example.annakocheshkova.testapplication.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Task created by user, should contain a name and time to expire (to be added)
 */
@DatabaseTable(tableName = "task")
public class Task
{
    /**
     * enum with available task statuses
     */
    public enum TaskStatus {
        Expired,
        Pending,
        Completed
    }

    @DatabaseField(generatedId = true)
    private
    int id;

    /**
     * name of the task
     */
    @DatabaseField
    private
    String name;

    /**
     * time when the task expires (don't mess with the notification time!)
     */
    @DatabaseField
    private
    long time;

    /**
     * if the task has a notification set
     */
    @DatabaseField
    private
    boolean notification;

    /**
     * time when the task should notify user
     */
    @DatabaseField
    private
    long alarmTime;

    /**
     * list of subtasks of this task
     */
    @ForeignCollectionField
    private Collection<SubTask> subTasks;


    public Task(){

    }

    /**
     * constructor that creates the instance of task by its name and time
     * @param name name of the task
     */
    public Task(String name, long time) {
        this.name = name;
        this.time = time;
    }

    /**
     * constructor to create an instance of task from another task
     * @param anotherTask task that needs to be copied
     */
    public Task(Task anotherTask) {
        this.id = anotherTask.getID();
        this.name = anotherTask.getName();
        this.time = anotherTask.getTime();
        this.alarmTime = anotherTask.getAlarmTime();
        this.notification = anotherTask.hasAlarms();
        subTasks = new ArrayList<>();
        for (SubTask subTask : anotherTask.getSubTasks())
            this.subTasks.add(subTask);
    }

    /**
     * calculate current task status depending on the current time
     * @return status
     */
    public TaskStatus getStatus() {
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        boolean completed = this.isCompleted();
        if (currentTime > this.getTime()) {
            if (completed) {
                return TaskStatus.Completed;
            } else {
                return TaskStatus.Expired;
            }
        } else {
            return TaskStatus.Pending;
        }
    }

    /**
     * set notification on task
     * @param show if to shown notification
     */
    public void setNotification(boolean show) {
        this.notification = show;
        if (!show)
            alarmTime = 0;
    }

    /**
     * sets the time of the notification
     * @param time to be set
     */
    public void setNotificationTime(long time) {
        this.alarmTime = time;
    }

    /**
     * to check if item is completed (checks the status of all the subtasks)
     * @return true if the task is completed, false if not
     */
    private boolean isCompleted() {
        for (SubTask subTask : subTasks)
            if (!subTask.getStatus())
                return false;
        return true;
    }


    /**
     * get all the subtasks of this task
     * @return subtasks
     */
    public Collection<SubTask> getSubTasks() {
        return subTasks;
    }

    /**
     * get the time of the alarm
     * @return time of the alarm
     */
    public long getAlarmTime() {
        return alarmTime;
    }

    /**
     * if the task should notify user
     * @return true if it should, false if not
     */
    public boolean hasAlarms() {
        return notification;
    }

    /**
     * Gets the value of name and returns it
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of time and returns it
     * @return time
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the name
     * @param name new value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the time
     * @param time new value
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets the value of id and returns it
     * @return id
     */
    public int getID() {
        return id;
    }
}
