package com.example.annakocheshkova.testapplication.Model;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
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

    /**
     * if the task has subtasks
     * @return true if has, false if not
     */
    public boolean hasSubTasks() {
        return subTasks.size() > 0;
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
        return alarms.size() > 0;
    }

    /**
     * constructor
     * @param name name of the task
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * constructor for copying
     * @param anotherTask task that needs to be copied before deleting
     */
    public Task(Task anotherTask) {
        this.id = anotherTask.getID();
        this.name = anotherTask.getName();
        subTasks = new ArrayList<>();
        alarms = new ArrayList<>();
        for (SubTask subTask : anotherTask.getSubTasks())
            this.subTasks.add(subTask);
        for (AlarmInfo alarm : anotherTask.getAlarms())
            this.alarms.add(alarm);
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
