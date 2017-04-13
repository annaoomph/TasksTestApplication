package com.example.annakocheshkova.testapplication.manager.exporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface for exporting items
 */
public interface Exporter<T> {

    /**
     * Exports items
     * @param items to be exported
     * @param path path where to export
     * @param name name of the file (if necessary)
     * @param databaseVersion version of the database
     */
    void export(List<T> items, String path, String name, int databaseVersion) throws IOException, FileNotFoundException;
}
