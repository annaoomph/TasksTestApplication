package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.utils.exception.ReadFileException;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * A class for importing items from file
 */
class TaskFileImporter implements Importer<Task> {

    @Override
    public void importData(String pathToFile, ImportListener<Task> importListener) {
        File file = new File(pathToFile);
        try {
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream fileInputStream = new FileInputStream(file);
            int count = fileInputStream.read(bytes);
            fileInputStream.close();
            if (count < 0) {
                throw new IOException();
            }
            String fileText = new String(bytes);
            Gson gson = new Gson();
            importListener.onSuccess(gson.fromJson(fileText, Task[].class));
        } catch (JsonParseException e) {
            importListener.onError(e);
        } catch (IOException exception) {
            importListener.onError(new ReadFileException(file, exception));
        }
    }
}
