package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.model.Task;

import java.util.List;

/**
 * An interface for main task activity
 */
public interface TaskView {

    /**
     * Displays all the tasks
     * @param items tasks
     */
    void showItems(List<Task> items);

    /**
     * Shows toast with information that item is deleted and Cancel button
     * @param task deleted item to be shown in a Toast
     */
    void showCancelBar(Task task);

    /**
     * Opens list of subtasks from the chosen task
     * @param id task id
     */
    void openTask(int id);

    /**
     * Opens task for editing
     * @param id id of the chosen task
     */
    void editTask(int id);

    /**
     * Shows the login or logout button
     * @param show true if show login, false if show logout
     */
    void showLoginButton(boolean show);

    /**
     * Opens the login view
     */
    void showLoginScreen();
}
