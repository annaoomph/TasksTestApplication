package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.ExportView;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.manager.Exporter;
import com.example.annakocheshkova.testapplication.manager.TaskFileExporter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * A controller to handle export view
 */
public class ExportController {

    private Exporter exporter;

    /**
     * this controller's main view
     */
    private ExportView view;

    /**
     * dataStore to work with data
     */
    private DataStore dataStore;

    /**
     * creates new instance of the xport controller
     * @param view main view
     */
    public ExportController(ExportView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * called when the export is clicked
     */
    public void onExport() {
        boolean local = view.isLocal();
        String nameOrPath = view.getNameOrPath();
        List<Task> tasks = dataStore.getAllTasks();
        if (local) {
            exportLocal(nameOrPath, tasks);
        } else {
            exportRemote(nameOrPath, tasks);
        }
    }

    /**
     * Exports the list of tasks locally
     * @param name path to file on device
     * @param tasksList list of tasks to be exported
     */
    private void exportLocal(String name, List<Task> tasksList) {
        exporter = new TaskFileExporter();
        try {
            exporter.export(tasksList, view.getFolder(), name, dataStore.getVersion());
        } catch (FileNotFoundException exc) {
            view.showWrongFilePathError();
        }
        catch (IOException exception) {
            view.showIOError();
        }
        view.close();
    }

    /**
     * Exports the list of tasks on server
     * @param path path to server
     * @param tasksList list of tasks to be exported
     */
    private void exportRemote(String path, List<Task> tasksList) {}
}
