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

    /**
     * current Task task we are working with
     */
    private static int currentTask = -1;

    /**
     * currentTask subtask we are updating
     */
    private SubTask subTask;

    /**
     * deleted subtask (to be restored if needed)
     */
    private SubTask deletedItem;

    public SubTaskController(SubTaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * User started updating subtask (Event)
     * @param subTask subtask to be updated
     */
    public void onUpdate(SubTask subTask) {
        view.showDialog(subTask);
        this.subTask = subTask;
    }

    /**
     * user started creating a new subtask
     */
    public void onCreate() {
        view.showDialog(null);
        this.subTask = null;
    }

    /**
     * user finished creating or updating the subtask
     * @param newName new name of the subtask to be created or updated
     */
    public void onEditingEnded(String newName) {
        if (subTask != null) {
            subTask.setName(newName);
            subTask.setStatus(false);
            dataStore.updateSubTask(subTask);
            subTask = null;
        } else {
            SubTask item = new SubTask(newName, false);
            Task main = dataStore.getTask(currentTask);
            item.setTask(main);
            dataStore.createSubTask(item);
        }
        if (currentTask != -1)
            getAllByTask(true, currentTask);
    }

    /**
     * user changed status of the subTask
     * @param subTask subtask which status needs to be toggled
     */
    public void onStatusChanged(SubTask subTask) {
        subTask.setStatus(!subTask.getStatus());
        dataStore.updateSubTask(subTask);
        if (currentTask != -1)
            getAllByTask(true, currentTask);
    }

    /**
     * get a list of subtasks by their main task
     * @param updateView (to be deleted)
     * @param task_id id of the main task
     * @return list of subtasks
     */
    public List<SubTask> getAllByTask(boolean updateView, int task_id) {
        Task main = dataStore.getTask(task_id);
        currentTask = task_id;
        List<SubTask> list = dataStore.getAllSubtasksByTask(main);
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
        if (currentTask != -1 && updateView) {
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
        deletedItem = item;
        dataStore.deleteSubTask(item);
        if (currentTask != -1)
            getAllByTask(true, currentTask);
    }

    /**
     * restore deleted subtask (if cancel button was pressed)
     */
    public void restoreDeleted() {
        dataStore.createSubTask(deletedItem);
        if (currentTask != -1)
            getAllByTask(true, currentTask);
    }
}
