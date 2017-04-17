package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.Listener.ExportListener;

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
     * @param name name of the file (if necessary)
     * @param converter converts the data to some format
     * @param exportListener listens the export events
     */
    void exportData(List<T> items, String name, Converter<T> converter, ExportListener exportListener);
}
