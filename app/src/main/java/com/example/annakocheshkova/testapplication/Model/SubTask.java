package com.example.annakocheshkova.testapplication.Model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * SubTask connected to a certain task (a piece of action to do)
 */
@DatabaseTable(tableName = "subtask")
public class SubTask {

    @DatabaseField(generatedId = true)
    int id;

    /**
     * the main task of the subtask
     */
    @DatabaseField(columnName = "task_id", foreign = true)
    private Task task;

    /**
     * name of the subtask
     */
    @DatabaseField
    String name;

    /**
     * if the subtask is completed
     */
    @DatabaseField
    Boolean completed;

    public SubTask(){}

    /**
     * constructor
     * @param name name of the subtask
     * @param completed if it is finished
     */
    public SubTask(String name, Boolean completed) {
        this.completed = completed;
        this.name = name;
    }

    public int getTaskId() {
        return this.task.getID();
    }

    public int getID() {
        return this.id;
    }

    public boolean getStatus() {
        return this.completed;
    }

    public void setStatus(boolean s) {
        this.completed = s;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
