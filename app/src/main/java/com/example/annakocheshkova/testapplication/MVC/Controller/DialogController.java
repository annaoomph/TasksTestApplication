package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.DialogView;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.OnItemEditedListener;

import java.util.List;

/**
 * controller for alert dialog fragment
 */
public class DialogController {
    private DialogView view;
    private DataStore dataStore;
    private SubTask editingItem;
    private int mainTaskId;
    private OnItemEditedListener onItemEditedListener;

    public DialogController(DialogView dialogView) {
        this.view = dialogView;
        dataStore = DataStoreFactory.getDataStore();
    }

    public void setOnItemEditedListener(OnItemEditedListener onItemEditedListener) {
        this.onItemEditedListener = onItemEditedListener;
    }

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
