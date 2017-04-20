package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.mvc.view.ExportView;
import com.example.annakocheshkova.testapplication.mvc.model.Task;
import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.utils.exporter.Exporter;
import com.example.annakocheshkova.testapplication.utils.exporter.ExporterFactory;
import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;

import java.util.List;

/**
 * A controller to handle export view
 */
public class ExportController implements ExportListener {

    /**
     * Controller's main view
     */
    private ExportView view;

    /**
     * DataStore to work with data
     */
    private DataStore dataStore;

    /**
     * Creates new instance of the export controller
     * @param view main view
     */
    public ExportController(ExportView view) {
        this.view = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * Called when the view was loaded. Checks if the user is logged in
     */
    public void onViewLoaded() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        Boolean loggedIn = preferencesManager.getBoolean(PreferencesManager.LOGGED_IN);
        view.setLoggedIn(loggedIn);
    }

    /**
     * Called when the export is clicked
     */
    public void onExport() {
        boolean local = view.isLocal();
        String nameOrPath = view.getNameOrPath();
        List<Task> tasks = dataStore.getAllTasks();
        Exporter<Task> taskExporter = ExporterFactory.getTaskExporter(local ? ExporterFactory.ExportType.LOCAL_TO_FILE : ExporterFactory.ExportType.REMOTE);
        taskExporter.exportData(tasks, nameOrPath, FileManager.DEFAULT_PATH, this);
    }

    @Override
    public void onSuccess() {
        String fullPath = FileManager.DEFAULT_PATH + view.getNameOrPath();
        view.showSuccessMessage(fullPath);
        view.close();
    }

    @Override
    public void onError(BaseError error) {
        view.showError(error);
    }
}
