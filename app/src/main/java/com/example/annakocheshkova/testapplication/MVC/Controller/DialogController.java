package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.DialogView;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.OnItemEditedListener;

/**
 * controller for alert dialog fragment
 */
public class DialogController {

    /**
     * main view of the controller
     */
    private DialogView view;

    /**
     * datastore to work with data
     */
    private DataStore dataStore;

    /**
     * current item we are editing. null if creating
     */
    private SubTask editingItem;

    /**
     * id of the main task of the subtask we are editing
     */
    private int mainTaskId;

    /**
     * listener responding to the event when user finished editing or creating.
     */
    private OnItemEditedListener onItemEditedListener;

    /**
     * constructor. sets the datastore
     * @param dialogView main view
     */
    public DialogController(DialogView dialogView) {
        this.view = dialogView;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * set on item edited listener
     * @param onItemEditedListener listener
     */
    public void setOnItemEditedListener(OnItemEditedListener onItemEditedListener) {
        this.onItemEditedListener = onItemEditedListener;
    }

    /**
     * event when the main dialog window is loaded
     * shows the editing item name if needed, stores or resets it here if needed
     * @param taskId id of the main task
     * @param id if of the subtask we are editing
     */
    public void onDialogLoaded(int taskId, int id) {
        if (id >= 0) {
            SubTask subTask = dataStore.getSubTask(id);
            view.showEditingItem(subTask.getName());
            editingItem = subTask;
        } else {
            editingItem = null;
            mainTaskId = taskId;
        }
    }

    /**
     * event when editing ended
     * creates or updates subtask depending on what was happening and what was stored here
     * @param newName name of the new (or edited) subtask
     */
    public void onEditingEnded(String newName) {
        if (editingItem != null) {
            editingItem.setName(newName);
            dataStore.updateSubTask(editingItem);
        } else {
            SubTask newSubTask = new SubTask(newName, false);
            newSubTask.setTask(dataStore.getTask(mainTaskId));
            dataStore.createSubTask(newSubTask);
        }
        onItemEditedListener.onItemEdited();
    }
}
