package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.manager.importer.Importer;
import com.example.annakocheshkova.testapplication.manager.importer.ImporterFactory;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.ImportView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * A controller for import view
 */
public class ImportController {

    /**
     * Datastore instance to work with the data
     */
    private DataStore dataStore;

    /**
     * Main controller view
     */
    private ImportView view;

    private Importer<Task> taskImporter;

    /**
     * Creates an instance of importController
     * @param view main view
     */
    public ImportController(ImportView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
        taskImporter = ImporterFactory.getTaskImporter(true);
    }

    /**
     * Called when the view was loaded
     */
    public void onViewLoaded() {
        String folderPath = view.getFolderPath();
        List<File> files = FileManager.getFilesInFolder(folderPath);
        view.showFiles(files);
    }

    /**
     * Called when the user has chosen an item
     */
    public void onFileChosen() {
        view.redrawFiles();
    }

    /**
     * Called when the import button was clicked
     */
    public void onImport() {
        String path = view.getChosenFilePath();
        if (path.length() == 0) {
            view.showFileNotChosenError();
        } else {
            try {
                Task[] tasks = taskImporter.importData(path, Task[].class);
                dataStore.createTasks(tasks);
                view.close(tasks.length);
            } catch (Exception exception) {
                view.corruptFileError();
            }
        }
    }
}
