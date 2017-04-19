package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;

/**
 * Interface for importing items
 * @param <T> Type of the data
 */
public interface Importer<T> {

    /**
     * Imports items
     * @param path path to data locally or on server
     * @param type type of objects you want to import (note: pass this as SomeClass[].class)
     * @param converter converter for data
     * @param importListener listener of import events
     */
    void importData(String path, Class<T[]> type, Converter<T> converter, ImportListener<T> importListener);
}
