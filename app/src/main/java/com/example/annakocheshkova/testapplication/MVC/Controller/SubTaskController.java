package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MVC.View.SubTaskView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with subtasks
 */
public class SubTaskController {

    private DataStore dataStore;
    private SubTaskView view;
    private static int current = -1; //current task we are working with

    public SubTaskController(SubTaskView view){
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * create a subtask
     * @param name name of the subtask to be created
     */
    public void create(String name) {
        SubTask item = new SubTask(name, false);
        Task main = dataStore.getTask(current);
        item.setTask(main);
        dataStore.createSubTask(item);
        if (current!=-1)
            getAllByTask(true, current);
    }

    /**
     * create a subtask
     * @param item subtask to be created
     */
    public void create(SubTask item) {
        dataStore.createSubTask(item);
        if (current!=-1)
            getAllByTask(true, current);
    }

    /**
     * get a list of subtasks by their main task
     * @param updateView (to be deleted)
     * @param task_id id of the main task
     * @return list of subtasks
     */
    public List<SubTask> getAllByTask(boolean updateView, int task_id) {
        Task main = dataStore.getTask(task_id);
        current = task_id;
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
        if (current!=-1 && updateView)
        {
            view.showItems(list);
            view.showTitle(main.getName());
        }
        return list;
    }

    /**
     * delete a certain subtask
     * @param item subtask to be deleted
     */
    public void delete(SubTask item) {
        dataStore.deleteSubTask(item);
        if (current!=-1)
            getAllByTask(true, current);
    }

    /**
     * update a certain subtask
     * @param item subtask to be deleted
     */
    public void update(SubTask item) {
        dataStore.updateSubTask(item);
        if (current!=-1)
            getAllByTask(true, current);
    }
}
