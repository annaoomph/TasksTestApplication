package com.example.annakocheshkova.testapplication.database;

import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.model.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that implements DataStore interface. Gets all the data from sqlite database datastore.
 */
class DatabaseDataStore implements DataStore {
    private DatabaseHelper databaseHelper;

    /**
     * Creates an instance of database datastore
     */
    DatabaseDataStore() {
        databaseHelper = DatabaseHelper.getInstance();
    }

    @Override
    public List<Task> getAllTasks(){
        return databaseHelper.getSimpleTaskDao().queryForAll();
    }

    @Override
    public List<SubTask> getAllSubtasksByTask(Task task) {
        return  databaseHelper.getSimpleSubTaskDao().queryForEq("task_id", task);
    }

    @Override
    public void createTask(Task item) {
        databaseHelper.getSimpleTaskDao().create(item);
        if (item.getSubTasks() != null)
            for (SubTask subTask : item.getSubTasks()) {
                subTask.setTask(item);
                databaseHelper.getSimpleSubTaskDao().create(subTask);
            }
    }

    @Override
    public void createSubTask(SubTask item) {
        databaseHelper.getSimpleSubTaskDao().create(item);
    }

    @Override
    public void updateTask(Task item) {
        databaseHelper.getSimpleTaskDao().update(item);
    }

    @Override
    public void updateSubTask(SubTask item) {
        databaseHelper.getSimpleSubTaskDao().update(item);
    }

    @Override
    public void deleteTask(Task item) {
        databaseHelper.getSimpleTaskDao().delete(item);
        deleteSubTasksByTask(item);
    }

    @Override
    public void deleteSubTask(SubTask item) {
        databaseHelper.getSimpleSubTaskDao().delete(item);
    }

    @Override
    public Task getTask(int id) {
        return databaseHelper.getSimpleTaskDao().queryForId(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        return  databaseHelper.getSimpleSubTaskDao().queryForId(id);
    }

    public List<Task> getAllTasksWithAlarms() {
        return databaseHelper.getSimpleTaskDao().queryForEq("notification", true);
    }

    @Override
    public void deleteSubTasksByTask(Task task) {
        List<SubTask> alarms = getAllSubtasksByTask(task);
        databaseHelper.getSimpleSubTaskDao().delete(alarms);
    }

    @Override
    public void createTasks(Task[] items) {
        ArrayList<Task> tasksList = new ArrayList<>(Arrays.asList(items));
        databaseHelper.getSimpleTaskDao().create(tasksList);
        for (Task task : tasksList) {
            if (task.getSubTasks() != null) {
                for (SubTask subTask : task.getSubTasks()) {
                    subTask.setTask(task);
                    databaseHelper.getSimpleSubTaskDao().create(subTask);
                }

            }
        }

    }

}
