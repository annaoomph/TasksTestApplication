package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.operation.TaskImportOperation;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

/**
 * An importer from server
 */
class TaskRemoteImporter implements Importer<Task> {

    @Override
    public void importData(String path, String date, final ImportListener<Task> importListener) {
        TaskImportOperation taskImportOperation = new TaskImportOperation(path, date, new OperationListener<TaskImportOperation>() {

            @Override
            public void onSuccess(TaskImportOperation operation) {
                importListener.onSuccess(operation.getItems());
            }

            @Override
            public void onFailure(BaseError baseError) {
                importListener.onError(baseError);
            }
        });
        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(taskImportOperation);
    }
}