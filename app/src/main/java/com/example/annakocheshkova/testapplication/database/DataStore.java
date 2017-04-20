package com.example.annakocheshkova.testapplication.database;

import com.example.annakocheshkova.testapplication.mvc.model.SubTask;
import com.example.annakocheshkova.testapplication.mvc.model.Task;
import java.util.List;

/**
 * An interface of the datastore to get all the data from whatever datastore there is
 */
public interface DataStore {

    /**
     * Gets the list of all the tasks
     * @return list of all the tasks
     */
    List<Task> getAllTasks();

    /**
     * Gets the list of the subtasks connected with a specific task
     * @param task the main task
     * @return list of the subtasks
     */
    List<SubTask> getAllSubtasksByTask(Task task);

    /**
     * Creates a new task
     * @param item new task
     */
    void createTask(Task item);

    /**
     * Creates a new subtask
     * @param item new subtask
     */
    void createSubTask(SubTask item);

    /**
     * Updates a specific task
     * @param item task to be updated
     */
    void updateTask(Task item);

    /**
     * Updates a specific subtask
     * @param item subtask to be updated
     */
    void updateSubTask(SubTask item);

    /**
     * Deletes a specific task
     * @param item task to be deleted
     */
    void deleteTask(Task item);

    /**
     * Deletes all the subtasks of a certain task
     * @param task main task
     */
    void deleteSubTasksByTask(Task task);

    /**
     * Deletes a specific subtask
     * @param item subtask to be deleted
     */
    void deleteSubTask(SubTask item);

    /**
     * Gets a specific task
     * @param id if of the task you want to get
     * @return task
     */
    Task getTask(int id);

    /**
     * Gets a specific subtask
     * @param id id of the subtask you want to get
     * @return subtask
     */
    SubTask getSubTask(int id);

    /**
     * Gets all the tasks that should fire a notification
     * @return list of all the needed tasks
     */
    List<Task> getAllTasksWithAlarms();

}
