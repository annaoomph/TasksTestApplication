package com.example.annakocheshkova.testapplication.utils.exporter;

import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;

import java.util.List;

/**
 * Interface for exporting items
 * @param <T> type of the data
 */
public interface Exporter<T> {

    /**
     * Exports items
     * @param items to be exported
     * @param path to file locally or on server
     * @param name name of the file (if necessary)
     * @param exportListener listens the export events
     */
    void exportData(List<T> items, String name, String path, ExportListener exportListener);
}
