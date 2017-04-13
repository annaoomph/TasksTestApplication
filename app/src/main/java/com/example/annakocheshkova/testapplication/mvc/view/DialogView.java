package com.example.annakocheshkova.testapplication.mvc.view;

/**
 * A view for alert dialog fragment
 */
public interface DialogView {

    /**
     * Displays the editing name if needed
     * @param subTaskName name of the editing subtask
     */
    void showEditingItem(String subTaskName);
}
