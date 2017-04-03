package com.example.annakocheshkova.testapplication.Controllers;

import com.example.annakocheshkova.testapplication.DataStore;
import com.example.annakocheshkova.testapplication.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Models.AlarmInfo;
import com.example.annakocheshkova.testapplication.Models.Task;
import android.app.Activity;
import com.example.annakocheshkova.testapplication.Services.AlarmReceiver;
import com.example.annakocheshkova.testapplication.Views.MainTasksActivity;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskController {

    private DataStore dataStore;
    private Activity view;

    public TaskController(Activity view){
        this.view = view;
        dataStore = DataStoreFactory.getDataStore(view);
    }

    /**
     * get the list of all tasks in the database
     */
    public void getAll(){
        List<Task> cats = dataStore.getAllTasks();
        Collections.sort(cats, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        MainTasksActivity curView = (MainTasksActivity)view;
        curView.showItems(cats);
    }

    /**
     * create a new task
     * @param updateView (to be deleted)
     * @param item new task to be created
     */
    private void create(boolean updateView, Task item) {
        dataStore.createTask(item);
        if (updateView)
            getAll();
    }

    /**
     * get a task by its id
     * @param id id of the task
     * @return the task
     */
    public Task get(int id)
    {
        return dataStore.getTask(id);
    }

    /**
     * delete a certain task
     * @param item task to be deleted
     */
    public void delete(Task item) {
        dataStore.deleteTask(item);
        AlarmController ac = new AlarmController(view);
        ac.deleteByTaskId(item.getID());
        getAll();
    }

    /**
     * update a certain task
     * @param updateView (to be deleted)
     * @param item the task to be updated
     */
    private void update(boolean updateView, Task item) {
        dataStore.updateTask(item);
        if (updateView)
            getAll();
    }

    /**
     * create or update a certain task
     * @param task task to be created or updated
     * @param intervalDuration (to be deleted)
     * @param calendar contains the time of the notification to be scheduled
     * @param interval (to be deleted)
     * @param update if a task is new or needs to be updated
     * @param fireAlarm if the alarm is needed
     */
    public void addOrUpdateTask(Task task, int intervalDuration, Calendar calendar, int interval, boolean update, boolean fireAlarm) {
        AlarmController ac = new AlarmController(view);
        long mms = calendar.getTimeInMillis();
        if (update)
        {
            if (task.hasAlarms())
            {
                int del_id = ac.get(task.getID()).getID();
                ac.delete(del_id);
                AlarmReceiver.removeAlarm(del_id, view.getApplicationContext());
            }
            update(false, task);
        }
        else
            create(false, task);
        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, mms, (interval > 0) ? intervalDuration : -1);
            ac.create(alarm);
            AlarmReceiver.addAlarm(view,task,(interval>0)?intervalDuration:-1,mms, ac.getAlarmId() );
        }
    }
}
