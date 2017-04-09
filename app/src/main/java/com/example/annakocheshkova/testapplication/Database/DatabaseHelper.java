package com.example.annakocheshkova.testapplication.Database;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * A class that extends Ormlite Sqlite helper, works with database
 */
class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /**
     * name of the database we are working with
     */
    private static final String DATABASE_NAME = "tasks.db";

    /**
     * current version of the database (change to higher number if structure changes are made)
     */
    private static final int DATABASE_VERSION = 16;

    /**
     * Dao for tasks table
     */
    private RuntimeExceptionDao<Task, Integer> simpleTaskRuntimeDao;

    /**
     * Dao for subtasks table
     */
    private RuntimeExceptionDao<SubTask, Integer> simpleSubTaskRuntimeDao;

    /**
     * Dao for alarms table
     */
    private RuntimeExceptionDao<AlarmInfo, Integer> simpleAlarmRuntimeDao;

    /**
     * constructor that initializes all Daos
     */
    DatabaseHelper() {
        super(MyApplication.getAppContext(),DATABASE_NAME, null, DATABASE_VERSION);
        simpleTaskRuntimeDao = null;
        simpleSubTaskRuntimeDao = null;
        simpleAlarmRuntimeDao = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, SubTask.class);
            TableUtils.createTable(connectionSource, AlarmInfo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.dropTable(connectionSource, SubTask.class, true);
            TableUtils.dropTable(connectionSource, AlarmInfo.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * a method for datastore to get access to alarms dao
      * @return alarms dao
     */
    RuntimeExceptionDao<AlarmInfo, Integer> getSimpleAlarmInfoDao() {
        if (simpleAlarmRuntimeDao == null) {
            simpleAlarmRuntimeDao = getRuntimeExceptionDao(AlarmInfo.class);
        }
        return simpleAlarmRuntimeDao;
    }

    /**
     * a method for datastore to get access to tasks dao
     * @return tasks dao
     */
    RuntimeExceptionDao<Task, Integer> getSimpleTaskDao() {
        if (simpleTaskRuntimeDao == null) {
            simpleTaskRuntimeDao = getRuntimeExceptionDao(Task.class);
        }
        return simpleTaskRuntimeDao;
    }

    /**
     * a method for datastore to get access to subtask dao
     * @return subtask dao
     */
    RuntimeExceptionDao<SubTask, Integer> getSimpleSubTaskDao() {
        if (simpleSubTaskRuntimeDao == null) {
            simpleSubTaskRuntimeDao = getRuntimeExceptionDao(SubTask.class);
        }
        return simpleSubTaskRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        simpleTaskRuntimeDao = null;
        simpleSubTaskRuntimeDao = null;
        simpleAlarmRuntimeDao = null;
    }
}