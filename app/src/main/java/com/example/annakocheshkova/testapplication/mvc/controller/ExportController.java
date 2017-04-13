package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.mvc.view.ExportView;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.manager.exporter.Exporter;
import com.example.annakocheshkova.testapplication.manager.exporter.ExporterFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * A controller to handle export view
 */
public class ExportController {

    private Exporter<Task> taskExporter;

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
        taskExporter = ExporterFactory.getTaskExporter(local);
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
        try {
            taskExporter.exportData(tasksList, view.getFolder(), name);
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
