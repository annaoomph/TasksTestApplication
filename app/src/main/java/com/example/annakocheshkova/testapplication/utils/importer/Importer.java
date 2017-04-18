package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.utils.converter.Converter;

import java.io.IOException;

/**
 * Interface for importing items
 * @param <T> Type of the data
 */
public interface Importer<T> {

    /**
     * Imports items
     * @param path path to data locally or on server
     * @param type type of objects you want to import (note: pass this as SomeClass[].class)
     * @return an array of objects
     */
    T[] importData(String path, Class<T[]> type, Converter<T> converter) throws IOException;
}
