package com.example.annakocheshkova.testapplication.utils.importer;

import com.example.annakocheshkova.testapplication.operation.ImportOperation;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.ImportListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

/**
 * An importer from server
 * @param <T> type of the data
 */
class RemoteImporter<T> implements Importer<T> {

    @Override
    public void importData(String path, Class<T[]> type, Converter<T> converter, final ImportListener<T> importListener) {
        ImportOperation<T> importOperation = new ImportOperation<>(path, converter, type, new OperationListener<ImportOperation<T>>() {

            @Override
            public void onSuccess(ImportOperation<T> operation) {
                    importListener.onSuccess(operation.getItems());
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                importListener.onError(connectionError);
            }
        });
        importOperation.execute();
    }
}