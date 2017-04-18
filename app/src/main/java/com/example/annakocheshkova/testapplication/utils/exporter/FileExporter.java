package com.example.annakocheshkova.testapplication.utils.exporter;

import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.FileError;
import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;

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
    public void exportData(List<T> items, String name, String path, Converter<T> converter, ExportListener exportListener) {
        try {
            FileManager.createFolder(path);
            String formattedData = converter.convert(items);
            File file = new File(path + File.separator + name);
            if (!file.createNewFile())
                exportListener.onError(new FileError(FileError.FileErrorType.CREATE_FILE_ERROR));
            else {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(formattedData.getBytes());
                outputStream.close();
            }
            exportListener.onSuccess();
        } catch (IOException exception) {
            exportListener.onError(new FileError(FileError.FileErrorType.IO_ERROR));
        }
    }
}
