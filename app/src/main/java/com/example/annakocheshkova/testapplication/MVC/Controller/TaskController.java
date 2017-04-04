package com.example.annakocheshkova.testapplication.MVC.Controller;
import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.Model.Alarm.CustomAlarmManager;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.MVC.View.TaskView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a controller which handles all the actions connected with tasks
 */
public class TaskController {

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
     * deleted task (to be restored if needed)
     */
    private Task deletedItem;

    /**
     * list of deleted with the task subtasks (to be restored, too)
     */
    private List<SubTask> deletedSubtasks;

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
                return firstTask.getName().compareTo(secondTask.getName());
            }
        });
        tasksList = tasks;
        view.showItems(tasks);
    }


    /**
     * restore deleted task (if cancel button was pressed)
     */
    public void onRestoreDeleted() {
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        if (deletedItem != null) {
            Task task = new Task(deletedItem.getName());
            dataStore.createTask(task);
            for (int i = 0; i < deletedSubtasks.size(); i++)
                deletedSubtasks.get(i).setTask(task);
            dataStore.createSubTasks(deletedSubtasks);
            if (deletedItem.hasAlarms()) {
                customAlarmManager.restoreDeleted(task);
            }
        }
        onViewLoaded();
    }

    /**
     * delete a certain task
     * @param position position of the chosen task to be deleted
     */
    public void onDelete(int position) {
        Task item = tasksList.get(position);
        deletedItem = item;
        deletedSubtasks = dataStore.getAllSubtasksByTask(item);
        dataStore.deleteTask(item);
        CustomAlarmManager customAlarmManager = new CustomAlarmManager();
        customAlarmManager.deleteByTaskId(item.getID());
        onViewLoaded();
        view.showCancelBar(deletedItem.getName());
    }

}
