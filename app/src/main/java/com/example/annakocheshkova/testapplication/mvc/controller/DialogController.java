package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.mvc.view.DialogView;
import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.utils.listener.OnItemEditedListener;

/**
 * A controller for alert dialog fragment
 */
public class DialogController {

    /**
     * Main view of the controller
     */
    private DialogView view;

    /**
     * Datastore to work with data
     */
    private DataStore dataStore;

    /**
     * Current item we are editing. null if creating
     */
    private SubTask editingItem;

    /**
     * Id of the main task of the subtask we are editing
     */
    private int mainTaskId;

    /**
     * Listener responding to the event when user finished editing or creating.
     */
    private OnItemEditedListener onItemEditedListener;

    /**
     * Creates new instance of DialogController
     * @param dialogView main view
     */
    public DialogController(DialogView dialogView) {
        this.view = dialogView;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * Sets on item edited listener
     * @param onItemEditedListener listener
     */
    public void setOnItemEditedListener(OnItemEditedListener onItemEditedListener) {
        this.onItemEditedListener = onItemEditedListener;
    }

    /**
     * Called when the main dialog window is loaded
     * Shows the editing item name if needed, stores or resets it here if needed
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
     * Called when editing ended
     * Creates or updates subtask depending on what was happening and what was stored here
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
