package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

/**
 * A factory to get the importer instance we need
 */
public class ImporterFactory {

    /**
     * Enum with import types
     */
    public enum ImportType {
        LOCAL_FROM_FILE,
        REMOTE
    }

    /**
     * Gets an instance of the task importer
     * @param importType type of the import
     * @return instance of the importer
     */
    public static Importer<Task> getTaskImporter(ImportType importType) {
        switch (importType) {
            case LOCAL_FROM_FILE:
                return new TaskFileImporter();
            case REMOTE:
                return new TaskRemoteImporter();
            default:
                throw new RuntimeException(new NotImplementedException(importType.toString()));
        }
    }
}