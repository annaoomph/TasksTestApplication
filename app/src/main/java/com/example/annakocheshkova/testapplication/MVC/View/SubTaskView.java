package com.example.annakocheshkova.testapplication.MVC.View;

import com.example.annakocheshkova.testapplication.Model.SubTask;

import java.util.List;

/**
 * Created by anna.kocheshkova on 4/3/2017.
 */

public interface SubTaskView {
    void showItems(List<SubTask> items);
    void showTitle(String title);
    void showDialog(SubTask subTask);
}
