package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.operation.TaskImportOperation;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

/**
 * An importer from server
 */
class TaskRemoteImporter implements Importer<Task> {

    @Override
    public void importData(String path, final ImportListener<Task> importListener) {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        int userId = preferencesManager.getUserId();
        TaskImportOperation taskImportOperation = new TaskImportOperation(path, userId, new OperationListener<TaskImportOperation>() {

            @Override
            public void onSuccess(TaskImportOperation operation) {
                importListener.onSuccess(operation.getItems());
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                importListener.onError(connectionError);
            }
        });
        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(taskImportOperation);
    }
}