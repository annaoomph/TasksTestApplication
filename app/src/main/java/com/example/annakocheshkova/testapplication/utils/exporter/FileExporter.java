package com.example.annakocheshkova.testapplication.utils.exporter;

import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.utils.exception.CreateFileException;
import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * A class for exporting items to file
 */
class FileExporter<T> implements Exporter<T> {

    @Override
    public void exportData(List<T> items, String name, String path, ExportListener exportListener) {
        try {
            FileManager.createFolder(path);
            Gson gson = new Gson();
            String formattedData = gson.toJson(items);
            File file = new File(path + File.separator + name);
            if (!file.createNewFile()) {
               exportListener.onError(new CreateFileException(file));
            }
            else {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(formattedData.getBytes());
                outputStream.close();
            }
            exportListener.onSuccess();
        } catch (IOException exception) {
            exportListener.onError(new CreateFileException(exception));
        }
    }
}
