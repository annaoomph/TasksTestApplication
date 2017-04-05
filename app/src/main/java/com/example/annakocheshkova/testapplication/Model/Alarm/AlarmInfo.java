package com.example.annakocheshkova.testapplication.Model.Alarm;

import com.example.annakocheshkova.testapplication.Model.Task;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * All the information about a certain alarm, needed to reschedule it
 */
@DatabaseTable(tableName = "alarms")
public class AlarmInfo {

    @DatabaseField(generatedId = true)
    private
    int id;

    /**
     * time when the notification should be fired (don't mess with expiration time!)
     */
    @DatabaseField
    private
    long time;

    /**
     * task the alarm is referring to
     */
    @DatabaseField(columnName = "task_id", foreign = true)
    private Task task;

    /**
     * constructor
     * @param task main task of the alarm
     * @param time time to be fired
     */
    public AlarmInfo(Task task, long time) {
        this.task = task;
        this.time = time;
    }

    public AlarmInfo(){}

    /**
     * get name of the task alarm is referring to
     * @return title
     */
    public String getTaskName() {
        return task.getName();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getID() {
        return this.id;
    }

    public long getTime() {
        return this.time;
    }

}
