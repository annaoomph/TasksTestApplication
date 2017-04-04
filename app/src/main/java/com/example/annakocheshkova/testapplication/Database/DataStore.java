package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;

import java.util.List;

/**
 * an interface of the datastore to get all the data from whatever datastore there is
 */
public interface DataStore {

    /**
     * get the list of all the tasks
     * @return list of all the tasks
     */
    List<Task> getAllTasks();

    /**
     * get the list of all the subtasks
     * @return list of all the subtasks
     */
    List<SubTask> getAllSubTasks();

    /**
     * get the list of the subtasks connected with a specific task
     * @param task the main task
     * @return list of the subtasks
     */
    List<SubTask> getAllSubtasksByTask(Task task);

    /**
     * get the list of all the active alarms at the moment (used at reboot)
     * @return list of all the alarms
     */
    List<AlarmInfo> getAllAlarms();

    /**
     * get the list of alarms connected with one specific task
     * @param id id of the task
     * @return list of all the alarms
     */
    List<AlarmInfo> getAllAlarmsByTaskId(int id);

    /**
     * create a new task
     * @param item new task
     * @return updated list of all the tasks
     */
    List<Task> createTask(Task item);

    /**
     * create a new subtask
     * @param item new subtask
     * @return updated list of all the subtasks
     */
    List<SubTask> createSubTask(SubTask item);

    /**
     * create a number of subtasks
     * @param items subtasks
     * @return updated list of subtasks
     */
    List<SubTask> createSubTasks(List<SubTask> items);

    /**
     * create a new AlarmInfo
     * @param item alarm
     * @return updated list of all the alarms
     */
    List<AlarmInfo> createAlarm(AlarmInfo item);

    /**
     * update a specific task
     * @param item task to be updated
     * @return updated list of tasks
     */
    List<Task> updateTask(Task item);

    /**
     * update a specific subtask
     * @param item subtask to be updated
     * @return updated list of all the subtasks
     */
    List<SubTask> updateSubTask(SubTask item);

    /**
     * delete a specific task
     * @param item task to be deleted
     * @return updated list of all the tasks
     */
    List<Task> deleteTask(Task item);

    /**
     * delete a specific subtask
     * @param item subtask to be deleted
     * @return updated list of all the subtasks
     */
    List<SubTask> deleteSubTask(SubTask item);

    /**
     * delete a specific alarm
     * @param item alarm to be deleted
     * @return updated list of all the alarms
     */
    List<AlarmInfo> deleteAlarm(AlarmInfo item);

    /**
     * delete a specific alarm
     * @param id id of the alarm to be deleted
     * @return updated list of all the alarms
     */
    List<AlarmInfo> deleteAlarm(int id);

    /**
     * get a specific task
     * @param id if of the task you want to get
     * @return task
     */
    Task getTask(int id);

    /**
     * get a specific subtask
     * @param id id of the subtask you want to get
     * @return subtask
     */
    SubTask getSubTask(int id);

    /**
     * get a specific alarm
     * @param id id of the alarm you want to get
     * @return alarm
     */
    AlarmInfo getAlarm(int id);
}
