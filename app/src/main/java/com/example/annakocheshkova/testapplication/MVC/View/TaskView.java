package com.example.annakocheshkova.testapplication.MVC.View;

import com.example.annakocheshkova.testapplication.Model.Task;

import java.util.List;

/**
 * Created by anna.kocheshkova on 4/3/2017.
 */

public interface TaskView {
    void showItems(List<Task> items);
}
