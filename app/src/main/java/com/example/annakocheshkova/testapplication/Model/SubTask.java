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
    @DatabaseField(columnName = "id_cat", foreign = true)
    private Task task;
    @DatabaseField
    String _name;
    @DatabaseField
    Boolean completed;

    public SubTask(){}
    public SubTask(String name, Boolean cmpl){
        this.completed = cmpl;
        this._name = name;
    }

    public boolean getStatus(){
        return this.completed;
    }
    public void setStatus(boolean s){
        this.completed = s;
    }
    public void setTask(Task task){
        this.task = task;
    }
    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name = name;
    }
}
