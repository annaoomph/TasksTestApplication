package com.example.annakocheshkova.testapplication.Model.Alarm;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import java.util.List;

/**
 * a controller which handles all the actions connected with alarms
 */
public class AlarmManager {

    private static int alarmId; //id of the last created alarm
    private static AlarmInfo deletedItem; // deleted item to be restored if Cancel button clicked
    private DataStore dataStore;

    public AlarmManager(){
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * delete alarm from the database
     * @param item alarm
     */
    private void delete(AlarmInfo item) {
        dataStore.deleteAlarm(item);
    }

    /**
     * delete alarm from the database
     * @param id id of the alarm to be deleted
     */
    public void delete(int id)  {
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
     */
    public void create(AlarmInfo item) {
        dataStore.createAlarm(item);
        alarmId = item.getID();
    }

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
        List<AlarmInfo> list = dataStore.getAllAlarmsByTaskId(id);
        for (int i=0; i<list.size(); i++)
        {
            deletedItem = list.get(i);
            AlarmReceiver.removeAlarm(deletedItem.getID());
            delete(list.get(i));
        }
    }

    public int getAlarmId()
    {
        return alarmId;
    }

    public AlarmInfo getDeletedItem()
    {
        return deletedItem;
    }
}