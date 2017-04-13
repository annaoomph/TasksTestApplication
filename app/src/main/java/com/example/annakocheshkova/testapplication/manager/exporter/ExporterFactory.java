package com.example.annakocheshkova.testapplication.manager.exporter;

import com.example.annakocheshkova.testapplication.model.Task;

/**
 * A factory to get the exporter instance we need
 */
public class ExporterFactory {

    public static Exporter<Task> getTaskExporter(boolean local) {
        if (local)
            return new FileExporter<Task>();
        else
            return new FileExporter<Task>();
        //TODO Set this to Remote Exporter
    }
}
