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

    void showCancelBar(String name);
}
