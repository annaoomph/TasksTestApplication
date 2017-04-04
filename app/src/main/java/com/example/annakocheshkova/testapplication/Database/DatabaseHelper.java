package com.example.annakocheshkova.testapplication.Database;
import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 4;
    private RuntimeExceptionDao<Task, Integer> simpleTaskRuntimeDao;
    private RuntimeExceptionDao<SubTask, Integer> simpleSubTaskRuntimeDao;
    private RuntimeExceptionDao<AlarmInfo, Integer> simpleAlarmRuntimeDao;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
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

    public RuntimeExceptionDao<AlarmInfo, Integer> getSimpleAIDao() {
        if (simpleAlarmRuntimeDao == null) {
            simpleAlarmRuntimeDao = getRuntimeExceptionDao(AlarmInfo.class);
        }
        return simpleAlarmRuntimeDao;
    }

    public RuntimeExceptionDao<Task, Integer> getSimpleTaskDao() {
        if (simpleTaskRuntimeDao == null) {
            simpleTaskRuntimeDao = getRuntimeExceptionDao(Task.class);
        }
        return simpleTaskRuntimeDao;
    }

    public RuntimeExceptionDao<SubTask, Integer> getSimpleSubTaskDao() {
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