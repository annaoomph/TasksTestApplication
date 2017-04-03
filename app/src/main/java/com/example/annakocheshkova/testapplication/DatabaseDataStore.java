package com.example.annakocheshkova.testapplication;

import android.content.Context;

import com.example.annakocheshkova.testapplication.Models.AlarmInfo;
import com.example.annakocheshkova.testapplication.Models.SubTask;
import com.example.annakocheshkova.testapplication.Models.Task;
import com.example.annakocheshkova.testapplication.Services.DatabaseHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

class DatabaseDataStore implements DataStore {

    private final RuntimeExceptionDao<AlarmInfo, Integer> simpleAlarmDao;
    private final RuntimeExceptionDao<Task, Integer> simpleTaskDao;
    private final RuntimeExceptionDao<SubTask, Integer> simpleSubTaskDao;

    DatabaseDataStore(Context context) {
        simpleAlarmDao = new DatabaseHelper(context).getSimpleAIDao();
        simpleTaskDao = new DatabaseHelper(context).getSimpleTaskDao();
        simpleSubTaskDao = new DatabaseHelper(context).getSimpleSubTaskDao();
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
    public List<SubTask> getAllSubtasksByTask(Task category) {
        return simpleSubTaskDao.queryForEq("id_cat", category);
    }

    @Override
    public List<AlarmInfo> getAllAlarms() {
        return simpleAlarmDao.queryForAll();
    }

    @Override
    public List<AlarmInfo> getAllAlarmsByTaskId(int id) {
        return simpleAlarmDao.queryForEq("id_cat", id);
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
    public List<SubTask> createSubTasks(List<SubTask> items) {
        simpleSubTaskDao.create(items);
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
        List<AlarmInfo> list = simpleAlarmDao.queryForEq("_id", id);
        simpleAlarmDao.delete(list.get(0));
        return getAllAlarms();
    }

    @Override
    public Task getTask(int id) {
        List<Task> list = simpleTaskDao.queryForEq("_id", id);
        return list.get(0);
    }

    @Override
    public AlarmInfo getAlarm(int id) {
        List<AlarmInfo> list = simpleAlarmDao.queryForEq("id_cat", id);
        return list.get(0);
    }
}
