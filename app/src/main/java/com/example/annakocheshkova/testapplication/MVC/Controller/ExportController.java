package com.example.annakocheshkova.testapplication.MVC.Controller;

import android.os.Environment;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.ExportView;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.Model.Task;
import com.example.annakocheshkova.testapplication.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * A controller to handle export view
 */
public class ExportController {

    /**
     * this controller's main view
     */
    private ExportView mainView;

    /**
     * dataStore to work with data
     */
    private DataStore dataStore;

    /**
     * creates new instance of the xport controller
     * @param view main view
     */
    public ExportController(ExportView view) {
        this.mainView = view;
        dataStore = DataStoreFactory.getDataStore();
    }

    /**
     * called when the export is clicked
     */
    public void onExport() {
        boolean local = mainView.isLocal();
        String nameOrPath = mainView.getNameOrPath();
        List<Task> tasks = dataStore.getAllTasks();
        if (local) {
            exportLocal(nameOrPath, tasks);
        } else {
            exportRemote(nameOrPath, tasks);
        }
    }

    /**
     * creates folder for a file if it doesn't exist
     * @return true if successful
     */
    private boolean createFolder() {
        File folder = new File(mainView.getFolder());
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        return success;
    }

    /**
     * Exports the list of tasks locally
     * @param name path to file on device
     * @param tasksList list of tasks to be exported
     */
    private void exportLocal(String name, List<Task> tasksList) {
        if (!createFolder())
            mainView.showWrongFilePathError();
         else {
            Gson gson = new Gson();
            int version = dataStore.getVersion();

            String tasks = version + "\n" + gson.toJson(tasksList);
            File file = new File(name);
            try {
                if (!file.createNewFile())
                    mainView.showIOError();
                else {
                    try {
                        OutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(tasks.getBytes());
                        outputStream.close();
                        mainView.close();
                    } catch (FileNotFoundException e) {
                        mainView.showWrongFilePathError();
                    }
                }
            } catch (IOException e) {
                mainView.showIOError();
            }
        }
    }

    /**
     * Exports the list of tasks on server
     * @param path path to server
     * @param tasksList list of tasks to be exported
     */
    private void exportRemote(String path, List<Task> tasksList) {}
}
