package com.example.annakocheshkova.testapplication.Model.Alarm;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import java.util.List;

/**
 * a controller which handles all the actions connected with alarms
 */
public class CustomAlarmManager {

    /**
     * deleted item to be restored if Cancel button was clicked
     */
    private static AlarmInfo deletedItem;
    private DataStore dataStore;

    public CustomAlarmManager() {
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * delete alarm from the database
     * @param id id of the alarm to be deleted
     */
    public void delete(int id) {
        dataStore.deleteAlarm(id);
    }

    /**
     * get all the alarms from the database
     * @return list of all the alarms
     */
    public List<AlarmInfo> getAll() {
        return dataStore.getAllAlarms();
    }

    /**
     * get a certain alarm from the database
     * @param id id of the needed alarm
     * @return alarm
     */
    public AlarmInfo get(int id) {
        return dataStore.getAlarm(id);
    }

    /**
     * create an alarm
     * @param item alarm
     * @return id of the created alarm
     */
    public int create(AlarmInfo item) {
        dataStore.createAlarm(item);
        return item.getID();
    }

    /**
     * restore the alarm which was deleted
     * @param task restored task (with the new id) which the alarm refers to
     */
    public void restoreDeleted(Task task) {
        if (deletedItem != null) {
            deletedItem.setTask(task);
            dataStore.createAlarm(deletedItem);
        }
    }

    /**
     * delete a certain alarm by its task id
     * @param id task id
     */
    public void deleteByTaskId(int id) {
        List<AlarmInfo> alarmList = dataStore.getAllAlarmsByTaskId(id);
        for (int i = 0; i < alarmList.size(); i++) {
            deletedItem = alarmList.get(i);
            AlarmReceiver.removeAlarm(deletedItem.getID());
            dataStore.deleteAlarm(alarmList.get(i));
        }
    }
}
