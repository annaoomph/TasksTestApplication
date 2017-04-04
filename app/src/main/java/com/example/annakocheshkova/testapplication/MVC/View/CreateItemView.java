package com.example.annakocheshkova.testapplication.MVC.View;

import com.example.annakocheshkova.testapplication.Model.Task;

/**
 * an interface for create item activity
 */
public interface CreateItemView {
    /**
     * shows the item to be edited
     * @param item item to be edited
     */
    void showItem(Task item);
}
