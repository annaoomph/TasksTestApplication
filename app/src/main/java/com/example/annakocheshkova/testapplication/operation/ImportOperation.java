package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for handling import http operations
 * @param <T> type of data to be exported
 */
public class ImportOperation<T> extends BaseOperation<T> {

    /**
     * Type of items
     */
    private Class<T[]> type;

    /**
     * Basic constructor. Creates an instance of the operation
     * @param url url to export items to
     * @param converter converter for items
     */
    public ImportOperation(String url, Converter<T> converter, OperationListener operationListener) {
        super(url, converter, operationListener);
    }

    /**
     * Creates an instance of operation
     * @param url to export items to
     * @param converter converter for items
     * @param type type of items to be imported
     */
    public ImportOperation(String url, Converter<T> converter, Class<T[]> type, OperationListener operationListener) {
        super(url, converter, operationListener);
        this.type = type;
    }

    public T[] getItems() {
        return converter.deconvert(responseObject.getItems(), type);
    }

    @Override
    public void onFakeResponse() {
       // if (type != null && type.getSimpleName() == "Task") {
            List<Task> fakeList = new ArrayList<>();
            fakeList.add(new Task("Test Name", 0 ));
            Converter<Task> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
            String fakeJson = converter.convert(fakeList);
            responseObject = new BaseResponse("token", 0, fakeJson);


        operationListener.onSuccess(this);
    }
}
