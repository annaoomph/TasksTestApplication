package com.example.annakocheshkova.testapplication.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * All the information about a certain alarm, needed to reschedule it
 */
@DatabaseTable(tableName = "alarms")
public class AlarmInfo {

    @DatabaseField(generatedId = true)
    private
    int _id;
    @DatabaseField
    private
    String _name;
    @DatabaseField
    private
    long time;
    @DatabaseField(columnName = "id_cat", foreign = true)
    private Task task;

    public AlarmInfo(Task cat, long time){
        this.task = cat;
        this._name = cat.getName();
        this.time = time;
    }

    public AlarmInfo(){}


    public void setTask(Task task){
        this.task = task;
    }
    public int getID(){return  this._id;}
    public String getName(){
        return this._name;
    }
    public long getTime(){
        return this.time;
    }

}
