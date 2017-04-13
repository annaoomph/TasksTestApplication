package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory.getConverter;

/**
 * A class for exporting tasks to file
 */
public class FileExporter<T> implements Exporter<T> {

    @Override
    public void export(List<T> items, String path, String name, int version) throws IOException, FileNotFoundException {
        if (!createFolder(path))
            throw new FileNotFoundException();
        else {
            Converter<T> converter = ConverterFactory.getConverter();
            String formattedData = converter.convert(items);
            String fileString = version + "\n" + formattedData;
            File file = new File(name);
            if (!file.createNewFile())
                throw new FileNotFoundException();
            else {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(fileString.getBytes());
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
