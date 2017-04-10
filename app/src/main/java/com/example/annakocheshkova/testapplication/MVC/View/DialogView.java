package com.example.annakocheshkova.testapplication.MVC.View;

/**
 * a view for alert dialog fragment
 */
public interface DialogView {

    /**
     * displays the editing name if needed
     * @param subTaskName name of the editing subtask
     */
    void showEditingItem(String subTaskName);
}
