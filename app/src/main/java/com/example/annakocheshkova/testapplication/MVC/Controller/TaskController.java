package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmManager;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import com.example.annakocheshkova.testapplication.UI.Activity.MainTasksActivity;
import com.example.annakocheshkova.testapplication.MVC.View.TaskView;

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
        view.showItems(cats);
    }


    public void restoreDeleted()
    {
        AlarmManager alarmManager = new AlarmManager();
        if (deletedItem != null) {
            Task task = new Task(deletedItem.getName());
            dataStore.createTask(task);
            for (int i=0; i<deletedSubtasks.size(); i++)
                deletedSubtasks.get(i).setTask(task);
            dataStore.createSubTasks(deletedSubtasks);
            if (deletedItem.hasAlarms()) {
                alarmManager.restoreDeleted(task);
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
        AlarmManager ac = new AlarmManager();
        ac.deleteByTaskId(item.getID());
        getAll();
    }

}
