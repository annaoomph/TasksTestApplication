package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;

import java.util.List;

/**
 * an interface to get all the data
 */
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
