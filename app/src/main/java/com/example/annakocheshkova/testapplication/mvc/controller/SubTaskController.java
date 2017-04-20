package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.mvc.model.SubTask;
import com.example.annakocheshkova.testapplication.mvc.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.SubTaskView;
import com.example.annakocheshkova.testapplication.utils.listener.OnItemEditedListener;
import com.example.annakocheshkova.testapplication.utils.listener.UndoListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A controller which handles all the actions connected with subtasks
 */
public class SubTaskController implements OnItemEditedListener, UndoListener<SubTask>{

    /**
     * Datastore example to work with the data
     */
    private DataStore dataStore;

    /**
     * Main controller view
     */
    private SubTaskView view;

    /**
     * Current task we are working with (-1 at first)
     */
    private static int currentTask = -1;

    /**
     * Creates an instance of SubTaskController
     * @param view main view
     */
    public SubTaskController(SubTaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * Called when user started updating subtask (Event)
     * @param subTask subtask to be updated
     */
    public void onUpdate(SubTask subTask) {
        view.showDialog(subTask, currentTask);
    }

    /**
     * Called when user started creating a new subtask
     */
    public void onCreate() {
        view.showDialog(null, currentTask);
    }

    /**
     * Called when user changed status of the subTask
     * @param subTask subtask which status needs to be toggled
     */
    public void onStatusChanged(SubTask subTask) {
        subTask.setStatus(!subTask.getStatus());
        dataStore.updateSubTask(subTask);
        reloadList();
    }

    /**
     * Called everytime view needs to be updated
     * Gets a list of subtasks by their main task
     * @param taskId id of the main task
     */
    public void onViewLoaded(int taskId) {
        if (taskId == -1) {
            view.showNoSuchTaskError();
        } else {
            Task task = dataStore.getTask(taskId);
            currentTask = taskId;
            if (task == null) {
                view.showNoSuchTaskError();
            } else {
                List<SubTask> list = dataStore.getAllSubtasksByTask(task);
                sort(list);
                view.showItems(list);
                view.showTitle(task.getName());
            }
        }
    }

    /**
     * Reloads the list of subtasks
     */
    private void reloadList() {
        Task task = dataStore.getTask(currentTask);
        if (task == null) {
            view.showNoSuchTaskError();
        } else {
            List<SubTask> list = dataStore.getAllSubtasksByTask(task);
            sort(list);
            view.showItems(list);
        }
    }

    /**
     * Called when user finished editing a subtask and the main view needs to be updated
     */
    @Override
    public void onItemEdited() {
        reloadList();
    }

    /**
     * Called when a subtask needs to be deleted
     * @param subTask to be deleted
     */
    public void onDelete(SubTask subTask) {
        view.showCancelBar(subTask);
        dataStore.deleteSubTask(subTask);
        reloadList();
    }

    @Override
    public void onUndo(SubTask item) {
        dataStore.createSubTask(item);
        reloadList();
    }

    /**
     * Sorts the given list of subtasks
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
