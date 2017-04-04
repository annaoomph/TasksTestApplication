package com.example.annakocheshkova.testapplication.Model;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;

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
     * list of subtasks of this task
     */
    @ForeignCollectionField
    private ForeignCollection<SubTask> subTasks;

    /**
     * list of the alarms of this task
     */
    @ForeignCollectionField
    private ForeignCollection<AlarmInfo> alarms;

    public Task(){

    }

    /**
     * constructor
     * @param id id of the task
     * @param name name of the task
     */
    public Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * if the task has alarms scheduled
     * @return true if it has, false if it has not
     */
    public boolean hasAlarms() {
        if (alarms.size() > 0)
            return true;
        return false;
    }

    /**
     * constructor
     * @param name name of the task
     */
    public Task(String name) {
        this.name = name;
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
}
