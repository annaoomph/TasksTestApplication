package com.example.annakocheshkova.testapplication.utils.exporter;

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
     */
    public static Exporter<Task> getTaskExporter(ExportType exportType){
        switch (exportType) {
            case LOCAL_TO_FILE:
                return new FileExporter<>();
            case REMOTE:
                return new RemoteExporter<>();
            default:
                throw new RuntimeException(new NotImplementedException(exportType.toString()));
        }
    }
}
