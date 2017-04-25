package com.example.annakocheshkova.testapplication.utils.exporter;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.operation.ExportOperation;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.List;

/**
 * An exporter to server
 * @param <T> type of the data
 */
class RemoteExporter<T> implements Exporter<T> {

    @Override
    public void exportData(List<T> items, String url, String path, final ExportListener exportListener) {
        ExportOperation<T> exportOperation = new ExportOperation<>(url, items, new OperationListener<ExportOperation>() {
            @Override
            public void onSuccess(ExportOperation operation) {
                String exportDate = operation.getExportDate();
                PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
                preferencesManager.addExportDate(exportDate);
                exportListener.onSuccess();
            }

            @Override
            public void onFailure(BaseError baseError) {
                exportListener.onError(baseError);
            }
        });

        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(exportOperation);
    }
}
