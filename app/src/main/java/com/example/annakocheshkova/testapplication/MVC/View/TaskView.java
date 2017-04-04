package com.example.annakocheshkova.testapplication.MVC.View;

import com.example.annakocheshkova.testapplication.Model.Task;

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
     * @param name name of the deleted item to be shown in a Toast
     */
    void showCancelBar(String name);
}
