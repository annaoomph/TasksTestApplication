package com.example.annakocheshkova.testapplication.utils.importer;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.FileError;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * A class for importing items from file
 * @param <T>
 */
class FileImporter<T> implements Importer<T> {

    @Override
    public void importData(String pathToFile, Class<T[]> type, Converter<T> converter, ImportListener<T> importListener) {
        try {
            File file = new File(pathToFile);
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream fileInputStream = new FileInputStream(file);
            int count = fileInputStream.read(bytes);
            fileInputStream.close();
            if (count < 0) {
                throw new IOException();
            }
            String fileText = new String(bytes);
            importListener.onSuccess(converter.deconvert(fileText, type));
        } catch (IOException exception) {
            importListener.onError(new FileError(FileError.FileErrorType.IO_ERROR));
        }
        catch (IllegalStateException exception) {
            importListener.onError(new FileError(FileError.FileErrorType.PARSE_ERROR));
        }
    }
}
