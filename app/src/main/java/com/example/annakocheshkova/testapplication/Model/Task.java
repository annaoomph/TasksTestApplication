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
    int _id;
    @DatabaseField
    String _name;
    @ForeignCollectionField
    private ForeignCollection<SubTask> subTasks;

    @ForeignCollectionField
    private ForeignCollection<AlarmInfo> alarms;

    public Task(){

    }

    public Task(int id, String name){
        this._id = id;
        this._name = name;
    }

    public boolean hasAlarms(){
        if (alarms.size()>0)
            return true;
        return false;
    }

    public Task(String name){
        this._name = name;
    }

    public int getID(){
return this._id;
}
    public void setID(int id){
this._id = id;
}
    public String getName(){
return this._name;
}
    public void setName(String name){
this._name = name;
}

}
