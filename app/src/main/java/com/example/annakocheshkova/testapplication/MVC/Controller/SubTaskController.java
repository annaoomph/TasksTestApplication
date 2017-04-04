package com.example.annakocheshkova.testapplication.MVC.Controller;

import android.net.sip.SipAudioCall;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MVC.View.SubTaskView;
import com.example.annakocheshkova.testapplication.OnItemEditedListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with subtasks
 */
public class SubTaskController implements OnItemEditedListener{

    @Override
    public void onItemEdited() {
        if (currentTask != -1)
            onViewLoaded(true, currentTask);
    }

    private List<SubTask> subTasksList;
    private DataStore dataStore;
    private SubTaskView view;

    /**
     * current Task task we are working with
     */
    private static int currentTask = -1;

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
            onViewLoaded(true, currentTask);
    }

    /**
     * onViewLoaded a list of subtasks by their main task
     * @param updateView (to be deleted)
     * @param task_id id of the main task
     * @return list of subtasks
     */
    public List<SubTask> onViewLoaded(boolean updateView, int task_id) {
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
        subTasksList = list;
        return list;
    }

    /**
     * onDelete a certain subtask
     * @param position position of the subtask to be deleted
     */
    public void onDelete(int position) {
        SubTask subTask = subTasksList.get(position);
        deletedItem = subTask;
        dataStore.deleteSubTask(subTask);
        if (currentTask != -1)
            onViewLoaded(true, currentTask);
        view.showCancelBar(deletedItem.getName());
    }

    /**
     * restore deleted subtask (if cancel button was pressed)
     */
    public void onRestoreDeleted() {
        dataStore.createSubTask(deletedItem);
        if (currentTask != -1)
            onViewLoaded(true, currentTask);
    }
}
