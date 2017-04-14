package com.example.annakocheshkova.testapplication.manager.importer;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A class for importing items from file
 * @param <T>
 */
class FileImporter<T> implements Importer<T> {

    @Override
    public T[] importData(String pathToFile, Class<T[]> type, Converter<T> converter) throws Exception {
        File file = new File(pathToFile);
        String fileText = readFromFile(file);
        return converter.deconvert(fileText, type);
    }

    /**
     * Reads data from file
     * @param file to be read
     * @return text in file
     */
    private String readFromFile(File file) throws FileNotFoundException, IOException {
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(bytes);
        fileInputStream.close();
        return new String(bytes);
    }
}
