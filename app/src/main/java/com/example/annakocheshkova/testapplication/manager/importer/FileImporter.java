package com.example.annakocheshkova.testapplication.manager.importer;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory;

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
    public T[] importData(String pathToFile, Class<T[]> type) throws Exception {
        File file = new File(pathToFile);
        String fileText = readFromFile(file);
        Converter<T> converter = ConverterFactory.getConverter();
        return converter.deConvert(fileText, type);
    }

    /**
     * Reads data from file
     * @param file to be read
     * @return text in file
     */
    private String readFromFile(File file) {
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                fileInputStream.read(bytes);
                fileInputStream.close();
                return new String(bytes);
            } catch (IOException e) {
                return "";
            }
        } catch (FileNotFoundException e) {
            return "";
        }
    }
}
