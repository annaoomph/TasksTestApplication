package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

/**
 * A factory to get the exporter instance we need
 */
public class ExporterFactory {

    /**
     * Enum of all the export types
     */
    public enum ExportType {
        LOCAL_TO_FILE,
        REMOTE
    }

    /**
     * Gets an instance of the task exporter
     * @param exportType type of export
     * @return instanceof the task exporter
     * @throws NotImplementedException exception thrown if the import type was not implemented
     */
    public static Exporter<Task> getTaskExporter(ExportType exportType) throws NotImplementedException {
        switch (exportType) {
            case LOCAL_TO_FILE:
                return new FileExporter<>();
            case REMOTE:
                throw new NotImplementedException(exportType.toString());
            default:
                throw new NotImplementedException(exportType.toString());
        }
    }
}
