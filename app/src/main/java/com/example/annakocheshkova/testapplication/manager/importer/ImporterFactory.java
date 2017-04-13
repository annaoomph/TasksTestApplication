package com.example.annakocheshkova.testapplication.manager.importer;

import com.example.annakocheshkova.testapplication.model.Task;

/**
 * A factory to get the importer instance we need
 */
public class ImporterFactory {

        public static Importer<Task> getTaskImporter(boolean local) {
            if (local)
                return new FileImporter<Task>();
            else
                return new FileImporter<Task>();
            //TODO Set this to Remote Importer
        }
    }