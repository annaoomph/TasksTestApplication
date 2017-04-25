package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.utils.DateParser;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.response.ExportResponse;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;

/**
 * Class for handling export http operations
 * @param <T> type of data to be exported
 */
public class ExportOperation<T> extends BaseOperation {

    /**
     * Items to be exported
     */
    private List<T> items;

    /**
     * Custom response for export
     */
    private ExportResponse exportResponse;

    /**
     * Creates an instance of operation
     * @param url to export items to
     * @param items list of data to be exported
     */
    public ExportOperation(String url, List<T> items, OperationListener operationListener) {
        super(url, operationListener);
        this.items = items;
    }

    @Override
    RequestBody preparePostContent() {
        Gson gson = new Gson();
        return RequestBody.create(HttpClient.MEDIA_TYPE_JSON, gson.toJson(items));
    }

    @Override
    String prepareGetContent() {
        return "";
    }

    @Override
    void parseResponse(String responseJson) throws JsonParseException {
        Gson gson = new Gson();
        exportResponse = gson.fromJson(responseJson, ExportResponse.class);
    }

    @Override
    BaseResponse getBaseResponse() {
        return exportResponse;
    }

    @Override
    RequestType getRequestType() {
        return RequestType.POST;
    }

    @Override
    public void onFakeResponse() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        String dateString = DateParser.getInstance().parse(date);
        String fakeJson = "{code: 200, message:\"\", exportDate: \"" + dateString + "\"}";
        handleResponse(fakeJson);
    }

    /**
     * Gets the export date value from response
     * @return export date
     */
    public String getExportDate() {
        return exportResponse.getExportDate();
    }
}
