package com.example.annakocheshkova.testapplication.manager.exporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface for exporting items
 * @param <T> type of the data
 */
public interface Exporter<T> {

    /**
     * Exports items
     * @param items to be exported
     * @param path path where to export
     * @param name name of the file (if necessary)
     */
    void exportData(List<T> items, String path, String name) throws FileNotFoundException, IOException ;
}
