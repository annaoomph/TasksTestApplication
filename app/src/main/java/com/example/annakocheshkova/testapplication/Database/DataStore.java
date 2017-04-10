package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;

import java.sql.SQLException;
import java.util.List;

/**
 * an interface of the datastore to get all the data from whatever datastore there is
 */
public interface DataStore {

    /**
     * gets the list of all the tasks
     * @return list of all the tasks
     */
    List<Task> getAllTasks();

    /**
     * gets the list of all the subtasks
     * @return list of all the subtasks
     */
    List<SubTask> getAllSubTasks();

    /**
     * gets the list of the subtasks connected with a specific task
     * @param task the main task
     * @return list of the subtasks
     */
    List<SubTask> getAllSubtasksByTask(Task task);

    /**
     * creates a new task
     * @param item new task
     */
    void createTask(Task item);

    /**
     * creates a new subtask
     * @param item new subtask
     */
    void createSubTask(SubTask item);

    /**
     * updates a specific task
     * @param item task to be updated
     */
    void updateTask(Task item);

    /**
     * updates a specific subtask
     * @param item subtask to be updated
     */
    void updateSubTask(SubTask item);

    /**
     * deletes a specific task
     * @param item task to be deleted
     */
    void deleteTask(Task item);

    /**
     * deletes all the subtasks of a certain task
     * @param task main task
     */
    void deleteSubTasksByTask(Task task);

    /**
     * deletes a specific subtask
     * @param item subtask to be deleted
     */
    void deleteSubTask(SubTask item);

    /**
     * gets a specific task
     * @param id if of the task you want to get
     * @return task
     */
    Task getTask(int id);

    /**
     * gets a specific subtask
     * @param id id of the subtask you want to get
     * @return subtask
     */
    SubTask getSubTask(int id);

    /**
     * gets all the tasks that should fire a notification
     * @return list of all the needed tasks
     */
    List<Task> getAllTasksWithAlarms();
}
