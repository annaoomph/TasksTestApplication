package com.example.annakocheshkova.testapplication.manager;

import com.example.annakocheshkova.testapplication.Model.Task;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * A class for exporting tasks to file
 */
public class TaskFileExporter implements Exporter<Task> {

    @Override
    public void export(List<Task> items, String path, String name, int version) throws IOException, FileNotFoundException {
        if (!createFolder(path))
            throw new FileNotFoundException();
        else {
            Gson gson = new Gson();
            String tasks = version + "\n" + gson.toJson(items);
            File file = new File(name);
            if (!file.createNewFile())
                throw new FileNotFoundException();
            else {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(tasks.getBytes());
                outputStream.close();
            }
        }
    }

    /**
     * creates folder for a file if it doesn't exist
     * @return true if successful
     */
    private boolean createFolder(String folderPath) {
        File folder = new File(folderPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        return success;
    }
}
