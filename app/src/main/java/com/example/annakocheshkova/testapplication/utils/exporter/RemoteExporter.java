package com.example.annakocheshkova.testapplication.utils.exporter;

import com.example.annakocheshkova.testapplication.operation.ExportOperation;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.ExportListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.List;

/**
 * An exporter to server
 * @param <T> type of the data
 */
class RemoteExporter<T> implements Exporter<T> {

    @Override
    public void exportData(List<T> items, String url, String path, Converter<T> converter, final ExportListener exportListener) {
        ExportOperation<T> exportOperation = new ExportOperation<>(url, converter, items, new OperationListener<ExportOperation>() {
            @Override
            public void onSuccess(ExportOperation operation) {
                exportListener.onSuccess();
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                exportListener.onError(connectionError);
            }
        });
        exportOperation.execute();
    }
}
