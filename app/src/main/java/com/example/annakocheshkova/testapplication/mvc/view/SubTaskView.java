package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.mvc.model.SubTask;

import java.util.List;

/**
 * An interface for detailedTask activity
 */
public interface SubTaskView {
    /**
     * Shows all the subtasks
     * @param items subtasks
     */
    void showItems(List<SubTask> items);

    /**
     * Displays the title with the current task name
     * @param title task name
     */
    void showTitle(String title);

    /**
     * Shows alert dialog fragment to create or update subtask
     * @param subTask subtask to be updated (if there is no such subtask, then it is null)
     * @param taskId id of the main task
     */
    void showDialog(SubTask subTask, int taskId);

    /**
     * Shows toast with information that item is deleted and Cancel button
     * @param subTask deleted item to be shown in a Toast
     */
    void showCancelBar(SubTask subTask);

    /**
     * Shows error if it has happened during opening the task
     */
    void showNoSuchTaskError();
}
