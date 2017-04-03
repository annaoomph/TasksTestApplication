package com.example.annakocheshkova.testapplication.Views;

import com.example.annakocheshkova.testapplication.Models.SubTask;

import java.util.List;

/**
 * Created by anna.kocheshkova on 4/3/2017.
 */

public interface SubTaskView {
    void showItems(List<SubTask> items);
    void showTitle(String title);
}
