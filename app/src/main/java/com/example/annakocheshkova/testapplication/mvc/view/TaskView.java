package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.model.Task;

import java.util.List;

/**
 * an interface for main task activity
 */
public interface TaskView {

    /**
     * display all the tasks
     * @param items tasks
     */
    void showItems(List<Task> items);

    /**
     * show toast with information that item is deleted and Cancel button
     * @param task deleted item to be shown in a Toast
     */
    void showCancelBar(Task task);

    /**
     * open list of subtasks from the chosen task
     * @param id task id
     */
    void openTask(int id);

    /**
     * open task for editing
     * @param id id of the chosen task
     */
    void editTask(int id);
}
