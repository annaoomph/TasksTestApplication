package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.response.ImportResponse;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.Gson;

/**
 * Class for handling import http operations
 * @param <T> type of data to be exported
 */
abstract class ImportOperation<T> extends BaseOperation {

    /**
     * Custom response for import
     */
    ImportResponse importResponse;

    /**
     * Creates an instance of operation
     * @param url to export items to
     */
    ImportOperation(String url, int userId, OperationListener operationListener) {
        super(url + "?id="+userId, operationListener);
    }

    /**
     * Gets the imported items
     * @return items array
     */
    abstract public T[] getItems();

    @Override
    void parseResponse(String responseJson) {
        Gson gson = new Gson();
        importResponse = gson.fromJson(responseJson, ImportResponse.class);
    }

    @Override
    BaseResponse getBaseResponse() {
        return importResponse;
    }

}
