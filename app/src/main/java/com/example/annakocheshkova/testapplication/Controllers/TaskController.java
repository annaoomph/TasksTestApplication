package com.example.annakocheshkova.testapplication.Controllers;

import com.example.annakocheshkova.testapplication.DataStore;
import com.example.annakocheshkova.testapplication.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Models.AlarmInfo;
import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.Services.AlarmReceiver;
import com.example.annakocheshkova.testapplication.Views.MainTasksActivity;
import com.example.annakocheshkova.testapplication.Views.TaskView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with tasks
 */
public class TaskController {

    private DataStore dataStore;
    private TaskView view;
    private Task deletedItem;
    private List<SubTask> deletedSubtasks;

    public TaskController(TaskView view){
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
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
     * get the task by id
     */
    public void get(int id){
        Task task = dataStore.getTask(id);
        ArrayList<Task> t = new ArrayList<>();
        t.add(task);
        view.showItems(t);
    }

    /**
     * create a new task
     * @param name name of the new task
     */
    public void create(String name, Calendar calendar, boolean fireAlarm) {
        AlarmController ac = new AlarmController();
        long mms = calendar.getTimeInMillis();
        Task task = new Task(name);
        dataStore.createTask(task);
        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, mms);
            ac.create(alarm);
            AlarmReceiver.addAlarm(task,mms, ac.getAlarmId() );
        }
    }

    public void restoreDeleted()
    {
        AlarmController alarmController = new AlarmController();
        if (deletedItem != null) {
            Task task = new Task(deletedItem.getName());
            dataStore.createTask(task);
            for (int i=0; i<deletedSubtasks.size(); i++)
                deletedSubtasks.get(i).setTask(task);
            dataStore.createSubTasks(deletedSubtasks);
            if (deletedItem.hasAlarms()) {
                alarmController.restoreDeleted(task);
            }
        }
        getAll();
    }

    /**
     * delete a certain task
     * @param item task to be deleted
     */
    public void delete(Task item) {
        deletedItem = item;
        deletedSubtasks = dataStore.getAllSubtasksByTask(item);
        dataStore.deleteTask(item);
        AlarmController ac = new AlarmController();
        ac.deleteByTaskId(item.getID());
        getAll();
    }

    /**
     * update a certain task
     * @param id id of the task to be updated
     * @param name new name of the task to be updated
     */
    public void update(int id, String name, Calendar calendar, boolean fireAlarm) {
        AlarmController ac = new AlarmController();
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
