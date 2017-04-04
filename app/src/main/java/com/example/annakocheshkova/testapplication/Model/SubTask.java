package com.example.annakocheshkova.testapplication.Model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * SubTask connected to a certain task (a piece of action to do)
 */
@DatabaseTable(tableName = "subtask")
public class SubTask {

    @DatabaseField(generatedId = true)
    int _id;

    /**
     * the main task of the subtask
     */
    @DatabaseField(columnName = "id_cat", foreign = true)
    private Task task;

    /**
     * name of the subtask
     */
    @DatabaseField
    String _name;

    /**
     * if the subtask is completed
     */
    @DatabaseField
    Boolean completed;

    public SubTask(){}

    public SubTask(String name, Boolean completed) {
        this.completed = completed;
        this._name = name;
    }

    public int getID() {
        return this._id;
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
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
