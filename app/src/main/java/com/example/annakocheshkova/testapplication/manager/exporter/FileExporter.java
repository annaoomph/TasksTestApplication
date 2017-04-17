package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.Listener.ExportListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * A class for exporting items to file
 */
class FileExporter<T> implements Exporter<T> {

    @Override
    public void exportData(List<T> items, String name, Converter<T> converter, ExportListener exportListener) {
        try {
            String folder = FileManager.createFolder();
            String formattedData = converter.convert(items);
            File file = new File(folder + File.separator + name);
            if (!file.createNewFile())
                exportListener.onIOError();
            else {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(formattedData.getBytes());
                outputStream.close();
            }
            exportListener.onSuccess();
        }
        catch (IOException exception) {
            exportListener.onIOError();
        }
    }
}
