package com.example.annakocheshkova.testapplication.manager.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface for importing items
 * @param <T> Type of the data
 */
public interface Importer<T extends Object> {

    /**
     * Imports items
     * @param path path to data locally or on server
     * @param type type of objects you want to import (note: pass this as SomeClass[].class)
     * @return an array of objects
     */
    T[] importData(String path, Class<T[]> type) throws Exception;
}
