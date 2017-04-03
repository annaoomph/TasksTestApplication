package com.example.annakocheshkova.testapplication.Controllers;

import android.app.Activity;
import com.example.annakocheshkova.testapplication.DataStore;
import com.example.annakocheshkova.testapplication.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.Views.DetailedTaskActivity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with subtasks
 */
public class SubTaskController {

    private DataStore dataStore;
    private Activity view;
    private static Task current; //current task we are working with

    public SubTaskController(Activity view){
        this.view = view;
        dataStore = DataStoreFactory.getDataStore(view);
    }

    /**
     * create a number of subtasks
     * @param updateView (to be deleted)
     * @param items list of subtasks
     */
    public void create(boolean updateView, List<SubTask> items) {
        dataStore.createSubTasks(items);
        if (current!=null && updateView)
            getAllByTask(true, current);
    }

    /**
     * create a subtask
     * @param item subtask
     */
    public void create(SubTask item) {
        dataStore.createSubTask(item);
        if (current!=null)
            getAllByTask(true, current);
    }

    /**
     * get a list of subtasks by their main task
     * @param updateView (to be deleted)
     * @param main main task
     * @return list of subtasks
     */
    public List<SubTask> getAllByTask(boolean updateView, Task main) {
        current = main;
        List<SubTask> list = dataStore.getAllSubtasksByTask(main);
        Collections.sort(list, new Comparator<SubTask>() {
            @Override
            public int compare(SubTask lhs, SubTask rhs) {
                if (!lhs.getStatus() && rhs.getStatus()) {
                    return -1;
                }
                if (lhs.getStatus() && !rhs.getStatus()) {
                    return 1;
                }
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        if (current!=null && updateView)
        {
            DetailedTaskActivity curView = (DetailedTaskActivity)view;
            curView.showItems(list);
        }
        return list;
    }

    /**
     * delete a certain subtask
     * @param item subtask to be deleted
     */
    public void delete(SubTask item) {
        dataStore.deleteSubTask(item);
        if (current!=null)
            getAllByTask(true, current);
    }

    /**
     * update a certain subtask
     * @param item subtask to be deleted
     */
    public void update(SubTask item) {
        dataStore.updateSubTask(item);
        if (current!=null)
            getAllByTask(true, current);
    }
}
