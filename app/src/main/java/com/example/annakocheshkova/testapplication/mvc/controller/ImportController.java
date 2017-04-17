package com.example.annakocheshkova.testapplication.mvc.controller;

import android.os.Environment;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.database.DataStore;
import com.example.annakocheshkova.testapplication.database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.manager.importer.Importer;
import com.example.annakocheshkova.testapplication.manager.importer.ImporterFactory;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.mvc.view.ImportView;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

import java.io.File;
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
        List<File> files = FileManager.getFilesInFolder(Environment.getExternalStorageDirectory() + "/" + MyApplication.getAppContext().getString(R.string.folder_name) + "/");
        view.showFiles(files);
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
                Converter<Task> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
                Importer<Task> taskImporter = ImporterFactory.getTaskImporter(ImporterFactory.ImportType.LOCAL_FROM_FILE);
                Task[] tasks = taskImporter.importData(path, Task[].class, converter);
                for (Task task : tasks) {
                    dataStore.createTask(task);
                    if (task.hasAlarm()) {
                        ReminderAlarmManager.addAlarm(task);
                    }
                }
                view.showMessage(tasks.length);
                view.close();
            }
            catch (NotImplementedException exception) {
                view.showNotImplementedError(exception);
            }
            catch (Exception exception) {
                view.showCorruptFileError();
            }
        }
    }
}
