package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.client.BaseHttpClient;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Class for handling export http operations
 * @param <T> type of data to be exported
 */
public class ExportOperation<T> extends BaseOperation<T> {

    /**
     * Items to be exported
     */
    private List<T> items;

    /**
     * Basic constructor. Creates an instance of the operation
     * @param url url to export items to
     * @param converter converter for items
     */
    public ExportOperation(String url, Converter<T> converter, OperationListener operationListener) {
        super(url, converter, operationListener);
    }

    /**
     * Creates an instance of operation
     * @param url to export items to
     * @param converter converter for items
     * @param items list of data to be exported
     */
    public ExportOperation(String url, Converter<T> converter, List<T> items, OperationListener operationListener) {
        super(url, converter, operationListener);
        this.items = items;
    }

    @Override
    RequestBody prepareContent() {
        return RequestBody.create(BaseHttpClient.MEDIA_TYPE_JSON, converter.convert(items));
    }

    @Override
    public void onFakeResponse() {
        operationListener.onSuccess(this);
    }
}
