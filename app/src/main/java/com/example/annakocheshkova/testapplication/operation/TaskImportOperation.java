package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.model.Task;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.Gson;

import okhttp3.RequestBody;

/**
 * Class for handling tasks import http operations
 */
public class TaskImportOperation extends ImportOperation<Task> {

    /**
     * Date of export
     */
     private String date;

    /**
     * Creates an instance of operation
     *
     * @param url to export items to
     * @param operationListener listener of import events
     * @param date date of export
     */
    public TaskImportOperation(String url, String date, OperationListener operationListener) {
        super(url, operationListener);
        this.date = date;
    }

    @Override
    public Task[] getItems() {
        String jsonItems = importResponse.getItems();
        Gson gson = new Gson();
        return gson.fromJson(jsonItems, Task[].class);
    }

    @Override
    RequestBody preparePostContent() {
        return null;
    }

    @Override
    String prepareGetContent() {
        return "?exportDate=" + date;
    }

    @Override
    RequestType getRequestType() {
        return RequestType.GET;
    }

    @Override
    public void onFakeResponse() {
        String fakeJson = "{code:200, message:\'\', items:\'[{\"alarm_time\":0,\"id\":0,\"name\":\"TEST\",\"notification\":false,\"time\":0},{\"alarm_time\":0,\"id\":0,\"name\":\"TEST-1\",\"notification\":false,\"time\":0}]\'}";
        handleResponse(fakeJson);
    }
}
