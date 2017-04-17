package com.example.annakocheshkova.testapplication.manager.importer;

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
     * @throws NotImplementedException exception thrown if the import type was not implemented
     */
    public static Importer<Task> getTaskImporter(ImportType importType) throws NotImplementedException {
        switch (importType) {
            case LOCAL_FROM_FILE:
                return new FileImporter<>();
            case REMOTE:
                throw new NotImplementedException(importType.toString());
            default:
                throw new NotImplementedException(importType.toString());
        }
    }
}