package com.example.annakocheshkova.testapplication.MVC.View;

import com.example.annakocheshkova.testapplication.Model.SubTask;

import java.util.List;

/**
 * an interface for detailedTask activity
 */
public interface SubTaskView {
    /**
     * shows all the subtasks
     * @param items subtasks
     */
    void showItems(List<SubTask> items);

    /**
     * displays the title with the current task name
     * @param title task name
     */
    void showTitle(String title);

    /**
     * shows alert dialog fragment to create or update subtask
     * @param subTask subtask to be updated (if there is no such subtask, then it is null)
     */
    void showDialog(SubTask subTask);
}
