package com.example.annakocheshkova.testapplication.Model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * SubTask connected to a certain task (a piece of action to do)
 */
@DatabaseTable(tableName = "subtask")
public class SubTask {

    @DatabaseField(generatedId = true)
    private
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
    private
    String name;

    /**
     * aboolean field showing the subtask is completed
     */
    @DatabaseField
    private
    Boolean completed;

    public SubTask(){}

    /**
     * creates new instance of subtask by its name and status
     * @param name name of the subtask
     * @param completed if it is finished
     */
    public SubTask(String name, Boolean completed) {
        this.completed = completed;
        this.name = name;
    }

    /**
     * gets current subtask status
     * @return true if the task is completed, false if not
     */
    public boolean getStatus() {
        return this.completed;
    }

    /**
     * gets the value of id and returns it
     * @return id
     */
    public int getID() {
        return id;
    }

    /**
     * gets the value of task and returns it
     * @return task
     */
    public Task getTask() {
        return task;
    }

    /**
     * gets the value of name and returns it
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the id
     * @param id new value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets the task
     * @param task new value
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * sets the name
     * @param name new value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the completed
     * @param completed new value
     */
    public void setStatus(Boolean completed) {
        this.completed = completed;
    }
}
