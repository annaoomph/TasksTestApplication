package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;

/**
 * Interface for importing items
 */
public interface Importer<T> {

    /**
     * Imports items
     * @param path path to data locally or on server
     * @param importListener listener of import events
     * @param date date of export (if needed)
     */
    void importData(String path, String date, ImportListener<T> importListener);
}
