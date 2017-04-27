package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.operation.TaskImportOperation;
import com.example.annakocheshkova.testapplication.response.ImportResponse;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.Gson;

/**
 * An importer from server
 */
class TaskRemoteImporter implements Importer<Task> {

    @Override
    public void importData(String path, final ImportListener<Task> importListener) {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        int userId = preferencesManager.getUserId();
        TaskImportOperation taskImportOperation = new TaskImportOperation(path, userId);
        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(taskImportOperation, new OperationListener<ImportResponse>() {

            @Override
            public void onSuccess(ImportResponse response) {
                Gson gson = new Gson();
                importListener.onSuccess(gson.fromJson(response.getItems(), Task[].class));
            }

            @Override
            public void onFailure(Exception exception) {
                importListener.onError(exception);
            }
        });
    }
}