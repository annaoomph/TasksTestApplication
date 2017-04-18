package com.example.annakocheshkova.testapplication.utils.importer;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * A class for importing items from file
 * @param <T>
 */
class FileImporter<T> implements Importer<T> {

    @Override
    public T[] importData(String pathToFile, Class<T[]> type, Converter<T> converter) throws IOException {
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
        return converter.deconvert(fileText, type);
    }
}
