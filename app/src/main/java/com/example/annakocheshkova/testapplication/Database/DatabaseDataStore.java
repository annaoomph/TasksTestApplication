package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

/**
 * a class that implements DataStore interface. Gets all the data from sqlite database datastore.
 */
class DatabaseDataStore implements DataStore {

    /**
     * Dao for Tasks table
     */
    private final RuntimeExceptionDao<Task, Integer> simpleTaskDao;

    /**
     * Dao for subTasks table
     */
    private final RuntimeExceptionDao<SubTask, Integer> simpleSubTaskDao;

    /**
     * simple constructor that gets all the Daos
     */
    DatabaseDataStore() {
        simpleTaskDao = new DatabaseHelper().getSimpleTaskDao();
        simpleSubTaskDao = new DatabaseHelper().getSimpleSubTaskDao();
    }

    @Override
    public List<Task> getAllTasks(){
        return simpleTaskDao.queryForAll();
    }

    @Override
    public List<SubTask> getAllSubTasks(){
        return simpleSubTaskDao.queryForAll();
    }

    @Override
    public List<SubTask> getAllSubtasksByTask(Task task) {
        return simpleSubTaskDao.queryForEq("task_id", task);
    }

    @Override
    public void createTask(Task item) {
        simpleTaskDao.create(item);
    }

    @Override
    public void createSubTask(SubTask item) {
        simpleSubTaskDao.create(item);
    }

    @Override
    public void updateTask(Task item) {
        simpleTaskDao.update(item);
    }

    @Override
    public void updateSubTask(SubTask item) {
        simpleSubTaskDao.update(item);
    }

    @Override
    public void deleteTask(Task item) {
        simpleTaskDao.delete(item);
    }

    @Override
    public void deleteSubTask(SubTask item) {
        simpleSubTaskDao.delete(item);
    }

    @Override
    public Task getTask(int id) {
        return simpleTaskDao.queryForId(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        return simpleSubTaskDao.queryForId(id);
    }

    @Override
    public List<Task> getAllTasksWithAlarms() {
        return simpleTaskDao.queryForEq("notification", true);
    }

    @Override
    public void deleteSubTasksByTask(Task task) {
        List<SubTask> alarms = getAllSubtasksByTask(task);
        simpleSubTaskDao.delete(alarms);
    }

}
