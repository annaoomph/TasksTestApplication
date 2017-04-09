package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * a class that implements DataStore interface. Gets all the data from sqlite database datastore.
 */
class DatabaseDataStore implements DataStore {

    /**
     * Dao for AlarmInfo table
     */
    private final RuntimeExceptionDao<AlarmInfo, Integer> simpleAlarmDao;

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
        simpleAlarmDao = new DatabaseHelper().getSimpleAlarmInfoDao();
        simpleTaskDao = new DatabaseHelper().getSimpleTaskDao();
        simpleSubTaskDao = new DatabaseHelper().getSimpleSubTaskDao();
    }

    @Override
    public List<Task> getAllTasks() {
        return simpleTaskDao.queryForAll();
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return simpleSubTaskDao.queryForAll();
    }

    @Override
    public List<SubTask> getAllSubtasksByTask(Task task) {
        return simpleSubTaskDao.queryForEq("task_id", task);
    }

    @Override
    public List<AlarmInfo> getAllAlarms() {
        return simpleAlarmDao.queryForAll();
    }

    @Override
    public List<AlarmInfo> getAllAlarmsByTaskId(int id) {
        return simpleAlarmDao.queryForEq("task_id", id);
    }

    @Override
    public List<Task> createTask(Task item) {
        simpleTaskDao.create(item);
        return getAllTasks();
    }

    @Override
    public List<SubTask> createSubTask(SubTask item) {
        simpleSubTaskDao.create(item);
        return getAllSubTasks();
    }

    @Override
    public List<AlarmInfo> createAlarm(AlarmInfo item) {
        simpleAlarmDao.create(item);
        return getAllAlarms();
    }

    @Override
    public List<Task> updateTask(Task item) {
        simpleTaskDao.update(item);
        return getAllTasks();
    }

    @Override
    public List<SubTask> updateSubTask(SubTask item) {
        simpleSubTaskDao.update(item);
        return getAllSubTasks();
    }

    @Override
    public List<Task> deleteTask(Task item) {
        simpleTaskDao.delete(item);
        return getAllTasks();
    }

    @Override
    public void deleteSubTasksByTask(Task task) {
        List<AlarmInfo> alarms = getAllAlarmsByTaskId(task.getID());
        simpleAlarmDao.delete(alarms);
    }

    @Override
    public List<SubTask> deleteSubTask(SubTask item) {
        simpleSubTaskDao.delete(item);
        return getAllSubTasks();
    }

    @Override
    public List<AlarmInfo> deleteAlarm(AlarmInfo item) {
        simpleAlarmDao.delete(item);
        return getAllAlarms();
    }

    @Override
    public List<AlarmInfo> deleteAlarm(int id) {
        List<AlarmInfo> list = getAllAlarms();
        simpleAlarmDao.delete(list.get(0));
        return getAllAlarms();
    }

    @Override
    public Task getTask(int id) {
        List<Task> list = simpleTaskDao.queryForEq("id", id);
        if (list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    public SubTask getSubTask(int id) {
        List<SubTask> list = simpleSubTaskDao.queryForEq("id", id);
        return list.get(0);
    }

    @Override
    public AlarmInfo getAlarm(int id) {
        List<AlarmInfo> list = simpleAlarmDao.queryForEq("task_id", id);
        return list.get(0);
    }
}
