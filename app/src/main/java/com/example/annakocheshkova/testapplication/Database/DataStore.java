package com.example.annakocheshkova.testapplication.Database;

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
     * create a new task
     * @param item new task
     */
    void createTask(Task item);

    /**
     * create a new subtask
     * @param item new subtask
     */
    void createSubTask(SubTask item);

    /**
     * update a specific task
     * @param item task to be updated
     */
    void updateTask(Task item);

    /**
     * update a specific subtask
     * @param item subtask to be updated
     */
    void updateSubTask(SubTask item);

    /**
     * delete a specific task
     * @param item task to be deleted
     */
    void deleteTask(Task item);

    /**
     * method deleting all the subtasks of a certain task
     * @param task main task
     */
    void deleteSubTasksByTask(Task task);

    /**
     * delete a specific subtask
     * @param item subtask to be deleted
     */
    void deleteSubTask(SubTask item);

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
     * get all the tasks that should fire a notification
     * @return list of all the needed tasks
     */
    List<Task> getAllTasksWithAlarms();
}
