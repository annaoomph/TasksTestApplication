package com.example.annakocheshkova.testapplication.mvc.model;

import com.google.gson.annotations.SerializedName;
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
     * Enum with available task statuses
     */
    public enum TaskStatus {
        Expired,
        Pending,
        Completed
    }

    @DatabaseField(generatedId = true)
    private
    @SerializedName("id") int id;

    /**
     * Name of the task
     */
    @DatabaseField
    private
    @SerializedName("name") String name;

    /**
     * The time when the task expires (don't mess with the notification time!)
     */
    @DatabaseField
    private
    @SerializedName("time") long time;

    /**
     * A boolean showing if the task has a notification set
     */
    @DatabaseField
    private
    @SerializedName("notification") boolean notification;

    /**
     * The time when the task should notify user
     */
    @DatabaseField
    private
    @SerializedName("alarm_time") long alarmTime;

    /**
     * List of subtasks of this task
     */
    @ForeignCollectionField (eager = true)
    @SerializedName("subtasks") private Collection<SubTask> subTasks;

    /**
     *  Creates the instance of the task
     */
    public Task(){

    }

    /**
     * Creates the instance of the task by its name and time
     * @param name name of the task
     */
    public Task(String name, long time) {
        this.name = name;
        this.time = time;
    }

    /**
     * Creates an instance of task from another task
     * @param anotherTask task that needs to be copied
     */
    public Task(Task anotherTask) {
        this.id = anotherTask.getID();
        this.name = anotherTask.getName();
        this.time = anotherTask.getTime();
        this.alarmTime = anotherTask.getAlarmTime();
        this.notification = anotherTask.hasAlarm();
        subTasks = new ArrayList<>();
        for (SubTask subTask : anotherTask.getSubTasks())
            this.subTasks.add(subTask);
    }

    /**
     * Calculates current task status depending on the current time
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
     * Sets notification on task
     * @param show if to shown notification
     */
    public void setNotification(boolean show) {
        this.notification = show;
        if (!show)
            alarmTime = 0;
    }

    /**
     * Sets the time of the notification
     * @param time to be set
     */
    public void setNotificationTime(long time) {
        this.alarmTime = time;
    }

    /**
     * Checks the status of all the subtasks
     * @return true if the task is completed, false if not
     */
    private boolean isCompleted() {
        for (SubTask subTask : subTasks)
            if (!subTask.getStatus())
                return false;
        return true;
    }

    /**
     * Checks if item has alarm scheduled
     * @return true if it has, false if it has not
     */
    public boolean hasAlarm() {
        return notification;
    }

    /**
     * Gets all the subtasks of this task
     * @return subtasks
     */
    public Collection<SubTask> getSubTasks() {
        return subTasks;
    }

    /**
     * Gets the time of the alarm
     * @return time of the alarm
     */
    public long getAlarmTime() {
        return alarmTime;
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
