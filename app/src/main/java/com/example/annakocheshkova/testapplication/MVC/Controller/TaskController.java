package com.example.annakocheshkova.testapplication.MVC.Controller;
import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Utils.NotificationAlarmManager;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MVC.View.TaskView;
import com.example.annakocheshkova.testapplication.Receiver.AlarmReceiver;
import com.example.annakocheshkova.testapplication.Utils.Listener.UndoListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with tasks
 */
public class TaskController implements UndoListener {

    /**
     * datastore to work with data
     */
    private DataStore dataStore;

    /**
     * main view
     */
    private TaskView view;

    /**
     * list of all the tasks
     */
    private List<Task> tasksList;

    /**
     * constructor
     * @param view main view
     */
    public TaskController(TaskView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * event called everytime you need to update the main view list of tasks
     */
    public void onViewLoaded() {
        List<Task> tasks = dataStore.getAllTasks();
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task firstTask, Task secondTask) {
                if (firstTask.getStatus().compareTo(secondTask.getStatus()) == 0) {
                    if (firstTask.getTime() > secondTask.getTime()) {
                        return 1;
                    } else if (firstTask.getTime() < secondTask.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else {
                    return firstTask.getStatus().compareTo(secondTask.getStatus());
                }
            }
        });
        tasksList = tasks;
        view.showItems(tasks);
    }

    /**
     * event when some task is chosen to be edited
     * @param id id of the chosen task
     */
    public void onItemUpdate(int id) {
        view.editTask(id);
    }

    /**
     * event when some task is chosen to be opened
     * @param id id of the chosen task
     */
    public void onItemChosen(int id) {
        view.openTask(id);
    }


    @Override
    public Task onDelete(int position) {
        Task item = tasksList.get(position);
        NotificationAlarmManager.removeAlarm(item);
        Task deletedItem = new Task(item);
        view.showCancelBar(item.getName());
        dataStore.deleteTask(item);
        dataStore.deleteSubTasksByTask(item);
        onViewLoaded();
        return deletedItem;
    }

    @Override
    public void onUndo(Object item) {
        Task deletedItem = (Task)item;
        if (deletedItem != null) {
            dataStore.createTask(deletedItem);
            for (SubTask subTask : deletedItem.getSubTasks()) {
                subTask.setTask(deletedItem);
                dataStore.createSubTask(subTask);
            }
            NotificationAlarmManager.addAlarm(deletedItem);
        }
        onViewLoaded();
    }
}
