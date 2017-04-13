package com.example.annakocheshkova.testapplication.mvc.view;

/**
 * a view for alert dialog fragment
 */
public interface DialogView {

    /**
     * display the editing name if needed
     * @param subTaskName name of the editing subtask
     */
    public void showEditingItem(String subTaskName);
}
