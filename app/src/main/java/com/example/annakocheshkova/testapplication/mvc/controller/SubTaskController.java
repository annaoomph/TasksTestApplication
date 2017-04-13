package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.SubTaskView;
import com.example.annakocheshkova.testapplication.utils.Listener.OnItemEditedListener;
import com.example.annakocheshkova.testapplication.utils.Listener.UndoListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with subtasks
 */
public class SubTaskController implements OnItemEditedListener, UndoListener<SubTask>{

    /**
     * event called when user finished editing a subtask and the main view needs to be updated
     */
    @Override
    public void onItemEdited() {
        if (currentTask != -1)
            onViewLoaded(currentTask);
    }

    /**
     * datastore example to work with the data
     */
    private DataStore dataStore;

    /**
     * main controller view
     */
    private SubTaskView view;

    /**
     * current task we are working with (-1 at first)
     */
    private static int currentTask = -1;

    /**
     * constructor
     * @param view main view
     */
    public SubTaskController(SubTaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * User started updating subtask (Event)
     * @param subTask subtask to be updated
     */
    public void onUpdate(SubTask subTask) {
        view.showDialog(subTask, currentTask);
    }

    /**
     * user started creating a new subtask
     */
    public void onCreate() {
        view.showDialog(null, currentTask);
    }

    /**
     * user changed status of the subTask
     * @param subTask subtask which status needs to be toggled
     */
    public void onStatusChanged(SubTask subTask) {
        subTask.setStatus(!subTask.getStatus());
        dataStore.updateSubTask(subTask);
        if (currentTask != -1)
            onViewLoaded(currentTask);
    }

    /**
     * event called everytime view needs to be updated
     * gets a list of subtasks by their main task
     * @param task_id id of the main task
     */
    public void onViewLoaded(int task_id) {
        Task main = dataStore.getTask(task_id);
        if (main == null) {
            view.showNoSuchTaskError();
        } else {
            currentTask = task_id;
            List<SubTask> list = dataStore.getAllSubtasksByTask(main);
            sort(list);
            if (currentTask != -1) {
                view.showItems(list);
                view.showTitle(main.getName());
            }
        }
    }

    /**
     * called when a subtask needs to be deleted
     * @param subTask to be deleted
     */
    public void onDelete(SubTask subTask) {
        view.showCancelBar(subTask);
        dataStore.deleteSubTask(subTask);
        if (currentTask != -1)
            onViewLoaded(currentTask);
    }

    @Override
    public void onUndo(SubTask item) {
        dataStore.createSubTask(item);
        if (currentTask != -1)
            onViewLoaded(currentTask);
    }

    /**
     * sorts the given list of subtasks
     * @param list of subtask
     */
    private void sort (List<SubTask> list) {
        Collections.sort(list, new Comparator<SubTask>() {
            @Override
            public int compare(SubTask firstSubTask, SubTask secondSubTask) {
                if (!firstSubTask.getStatus() && secondSubTask.getStatus()) {
                    return -1;
                }
                if (firstSubTask.getStatus() && !secondSubTask.getStatus()) {
                    return 1;
                }
                return firstSubTask.getName().compareTo(secondSubTask.getName());
            }
        });
    }

}
