package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.utils.importer.Importer;
import com.example.annakocheshkova.testapplication.utils.importer.ImporterFactory;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.ImportView;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;

import java.io.File;
import java.util.List;

/**
 * A controller for import view
 */
public class ImportController implements ImportListener<Task>{

    /**
     * Datastore instance to work with the data
     */
    private DataStore dataStore;

    /**
     * Main controller view
     */
    private ImportView view;

    /**
     * Creates an instance of importController
     * @param view main view
     */
    public ImportController(ImportView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * Called when the view was loaded
     */
    public void onViewLoaded() {
        List<File> files = FileManager.getFilesInFolder(FileManager.DEFAULT_PATH);
        view.showFiles(files);
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        Boolean loggedIn = preferencesManager.getLoggedIn();
        view.setLoggedIn(loggedIn);
    }

    /**
     * Called when the import button was clicked
     */
    public void onImport() {
        boolean local = view.isLocal();
        String path = view.getNameOrPath();
        if (path.length() == 0) {
            view.showFileNotChosenError();
        } else {
            Importer<Task> taskImporter = ImporterFactory.getTaskImporter(local ? ImporterFactory.ImportType.LOCAL_FROM_FILE : ImporterFactory.ImportType.REMOTE);
            taskImporter.importData(path, this);
        }
    }

    @Override
    public void onSuccess(Task[] tasks) {
        for (Task task : tasks) {
            dataStore.createTask(task);
            if (task.hasAlarm()) {
                ReminderAlarmManager.addAlarm(task);
            }
        }
        view.showSuccessMessage(tasks.length);
        view.close();
    }

    @Override
    public void onError(BaseError error) {
        view.showError(error);
    }
}
