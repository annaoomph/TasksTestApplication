package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.model.Task;
import com.google.gson.Gson;

import okhttp3.RequestBody;

/**
 * Class for sending tasks import http requests
 */
public class TaskImportOperation extends ImportOperation<Task> {

    /**
     * Id of the current user
     */
    private int userId;

    /**
     * Creates an instance of operation
     * @param url to export items to
     * @param userId id of the user
     */
    public TaskImportOperation(String url, int userId) {
        super(url);
        this.userId = userId;
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
        return "?id="+userId;
    }

    @Override
    RequestType getRequestType() {
        return RequestType.GET;
    }

    @Override
    public String getFakeResponseJson() {
        return "{code:200, message:\'\', items:\'[{\"alarm_time\":0,\"id\":0,\"name\":\"TEST\",\"notification\":false,\"time\":0},{\"alarm_time\":0,\"id\":0,\"name\":\"TEST-1\",\"notification\":false,\"time\":0}]\'}";
    }
}
