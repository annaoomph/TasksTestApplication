package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.CreateItemView;
import com.example.annakocheshkova.testapplication.MVC.View.TaskView;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmManager;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by anna.kocheshkova on 4/4/2017.
 */

public class CreateItemController {

    private DataStore dataStore;
    private CreateItemView view;

    public CreateItemController(CreateItemView view){
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * get the task by id
     */
    public void get(int id){
        Task task = dataStore.getTask(id);
        view.showItem(task);
    }

    /**
     * create a new task
     * @param name name of the new task
     */
    public void create(String name, Calendar calendar, boolean fireAlarm) {
        AlarmManager ac = new AlarmManager();
        long mms = calendar.getTimeInMillis();
        Task task = new Task(name);
        dataStore.createTask(task);
        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, mms);
            ac.create(alarm);
            AlarmReceiver.addAlarm(task,mms, ac.getAlarmId() );
        }
    }


    /**
     * update a certain task
     * @param id id of the task to be updated
     * @param name new name of the task to be updated
     */
    public void update(int id, String name, Calendar calendar, boolean fireAlarm) {
        AlarmManager ac = new AlarmManager();
        long mms = calendar.getTimeInMillis();
        Task task = dataStore.getTask(id);
        if (task.hasAlarms())
        {
            int del_id = ac.get(task.getID()).getID();
            ac.delete(del_id);
            AlarmReceiver.removeAlarm(del_id);
        }
        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, mms);
            ac.create(alarm);
            AlarmReceiver.addAlarm(task,mms, ac.getAlarmId() );
        }
        task.setName(name);
        dataStore.updateTask(task);
    }
}
