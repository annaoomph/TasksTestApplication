package com.example.annakocheshkova.testapplication;

import com.example.annakocheshkova.testapplication.Models.AlarmInfo;
import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.Models.Task;

import java.util.List;


public interface DataStore {

    List<Task> getAllTasks();
    List<SubTask> getAllSubTasks();
    List<SubTask> getAllSubtasksByTask(Task category);
    List<AlarmInfo> getAllAlarms();
    List<AlarmInfo> getAllAlarmsByTaskId(int id);

    List<Task> createTask(Task item);
    List<SubTask> createSubTask(SubTask item);
    List<SubTask> createSubTasks(List<SubTask> items);
    List<AlarmInfo> createAlarm(AlarmInfo item);

    List<Task> updateTask(Task item);
    List<SubTask> updateSubTask(SubTask item);

    List<Task> deleteTask(Task item);
    List<SubTask> deleteSubTask(SubTask item);
    List<AlarmInfo> deleteAlarm(AlarmInfo item);
    List<AlarmInfo> deleteAlarm(int id);

    Task getTask(int id);
    AlarmInfo getAlarm(int id);
}
