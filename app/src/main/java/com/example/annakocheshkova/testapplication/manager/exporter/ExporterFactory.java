package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.model.Task;

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

    public static Exporter<Task> getTaskExporter(ExportType exportType) {
        if (exportType == ExportType.LOCAL_TO_FILE) {
            return new FileExporter<>();
        } else {
            return new RemoteExporter<>();
        }
    }
}
