package com.example.annakocheshkova.testapplication.Model;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@DatabaseTable(tableName = "task")

/**
 * Task created by user, should contain a name and time to expire (to be added)
 */
public class Task
{
    @DatabaseField(generatedId = true)
    int id;

    /**
     * name of the task
     */
    @DatabaseField
    String name;

    /**
     * time when the task expires (don't mess with the notification time!)
     */
    @DatabaseField
    private
    long time;

    /**
     * list of subtasks of this task
     */
    @ForeignCollectionField
    private Collection<SubTask> subTasks;

    /**
     * list of the alarms of this task
     */
    @ForeignCollectionField
    private Collection<AlarmInfo> alarms;

    public Task(){

    }

    public TaskStatus getStatus() {
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        boolean completed = this.isCompleted();
        if (currentTime > this.getTime()) {
            if (completed) {
                return TaskStatus.Completed;
            } else {
                return TaskStatus.Uncompleted;
            }
        } else {
            return TaskStatus.Pending;
        }
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
     * get all the alarms of this task
     * @return alarms
     */
    public Collection<AlarmInfo> getAlarms() {
        return alarms;
    }

    /**
     * if the task has alarms scheduled
     * @return true if it has, false if it has not
     */
    public boolean hasAlarms() {
        return alarms.size() > 0;
    }

    /**
     * constructor
     * @param name name of the task
     */
    public Task(String name, long time) {
        this.name = name;
        this.time = time;
    }

    /**
     * constructor for copying
     * @param anotherTask task that needs to be copied before deleting
     */
    public Task(Task anotherTask) {
        this.id = anotherTask.getID();
        this.name = anotherTask.getName();
        this.time = anotherTask.getTime();
        subTasks = new ArrayList<>();
        alarms = new ArrayList<>();
        for (SubTask subTask : anotherTask.getSubTasks())
            this.subTasks.add(subTask);
        for (AlarmInfo alarm : anotherTask.getAlarms())
            this.alarms.add(alarm);
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum TaskStatus {
        Uncompleted,
        Pending,
        Completed
    }
}
