package com.example.annakocheshkova.testapplication.MVC.Controller;
import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.CreateItemView;
import com.example.annakocheshkova.testapplication.Model.Alarm.AlarmInfo;
import com.example.annakocheshkova.testapplication.Model.Alarm.CustomAlarmManager;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import java.util.Calendar;

/**
 * controller for create item view
 */
public class CreateItemController {

    private DataStore dataStore;
    private CreateItemView view;

    public CreateItemController(CreateItemView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * get the task by id
     * @param id id of the task to be shown
     */
    public void get(int id) {
        Task task = dataStore.getTask(id);
        view.showItem(task);
    }

    /**
     * create a new task
     * @param name name of the new task
     * @param calendar calendar contains time of the alarm
     * @param fireAlarm if reminder is needed
     */
    public void create(String name, Calendar calendar, boolean fireAlarm) {
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        long timeToSchedule = calendar.getTimeInMillis();
        Task task = new Task(name);
        dataStore.createTask(task);
        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, timeToSchedule);
            int alarmId = customAlarmManager.create(alarm);
            AlarmReceiver.addAlarm(task,timeToSchedule, alarmId);
        }
    }

    /**
     * update a certain task
     * @param id id of the task to be updated
     * @param name new name of the task to be updated
     * @param calendar calendar contains time of the alarm
     * @param fireAlarm if reminder is needed
     */
    public void update(int id, String name, Calendar calendar, boolean fireAlarm) {
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        long timeToSchedule = calendar.getTimeInMillis();
        Task task = dataStore.getTask(id);
        if (task.hasAlarms()) {
            int deletedAlarmId = customAlarmManager.get(task.getID()).getID();
            customAlarmManager.delete(deletedAlarmId);
            AlarmReceiver.removeAlarm(deletedAlarmId);
        }
        if (fireAlarm) {
            AlarmInfo alarm = new AlarmInfo(task, timeToSchedule);
            int alarmId = customAlarmManager.create(alarm);
            AlarmReceiver.addAlarm(task,timeToSchedule, alarmId );
        }
        task.setName(name);
        dataStore.updateTask(task);
    }
}
