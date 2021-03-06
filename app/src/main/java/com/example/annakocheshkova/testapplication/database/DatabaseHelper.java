package com.example.annakocheshkova.testapplication.database;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.annakocheshkova.testapplication.model.SubTask;
import com.example.annakocheshkova.testapplication.model.Task;
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
     * A database helper one and only instance
     */
    private static final DatabaseHelper databaseHelper = new DatabaseHelper();

    /**
     * Name of the database we are working with
     */
    private static final String DATABASE_NAME = "tasks.db";

    /**
     * Current version of the database (change to higher number if structure changes are made)
     */
    private static final int DATABASE_VERSION = 37;

    /**
     * Dao for tasks table
     */
    private RuntimeExceptionDao<Task, Integer> simpleTaskRuntimeDao;

    /**
     * Dao for subtasks table
     */
    private RuntimeExceptionDao<SubTask, Integer> simpleSubTaskRuntimeDao;

    /**
     * Creates new instance of database helper. Initializes all Daos
     */
    private DatabaseHelper() {
        super(MyApplication.getAppContext(),DATABASE_NAME, null, DATABASE_VERSION);
        simpleTaskRuntimeDao = null;
        simpleSubTaskRuntimeDao = null;
    }

    /**
     * Gets an instance of the Database Helper
     * @return instance
     */
    public static DatabaseHelper getInstance() {
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, SubTask.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.dropTable(connectionSource, SubTask.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets access to tasks dao
     * @return tasks dao
     */
    RuntimeExceptionDao<Task, Integer> getSimpleTaskDao() {
        if (simpleTaskRuntimeDao == null) {
            simpleTaskRuntimeDao = getRuntimeExceptionDao(Task.class);
        }
        return simpleTaskRuntimeDao;
    }

    /**
     * Gets access to subtask dao
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
    }

}