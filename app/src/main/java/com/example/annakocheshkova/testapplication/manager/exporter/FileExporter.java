package com.example.annakocheshkova.testapplication.manager.exporter;

import android.os.Environment;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
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
    public void exportData(List<T> items, String name, Converter<T> converter) throws FileNotFoundException, IOException  {
        String folder = FileManager.createFolder(FileManager.DEFAULT_PATH);
        String formattedData = converter.convert(items);
        File file = new File(folder + File.separator + name);
        if (!file.createNewFile())
            throw new FileNotFoundException();
        else {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(formattedData.getBytes());
            outputStream.close();
        }
    }
}
