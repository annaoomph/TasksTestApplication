package com.example.annakocheshkova.testapplication.manager.importer;

import com.example.annakocheshkova.testapplication.model.Task;

/**
 * A factory to get the importer instance we need
 */
public class ImporterFactory {

    public enum ImportType {
        LOCAL_FROM_FILE,
        REMOTE
    }

    public static Importer<Task> getTaskImporter(ImportType importType) {
        if (importType == ImportType.LOCAL_FROM_FILE) {
            return new FileImporter<>();
        } else {
            return new FileImporter<>();
        }    //TODO Set this to Remote Importer
    }
}