package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.model.Task;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;

/**
 * Class for handling tasks import http operations
 */
public class TaskImportOperation extends ImportOperation<Task> {

    /**
     * Id of the current user
     */
    private int userId;

    /**
     * Creates an instance of operation
     *
     * @param url to export items to
     * @param operationListener listener of import events
     * @param userId id of the user to get the items
     */
    public TaskImportOperation(String url, int userId, OperationListener operationListener) {
        super(url, operationListener);
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
    public void onFakeResponse() {
        String fakeJson = MyApplication.getAppContext().getString(R.string.fake_task_import_json);
        handleResponse(fakeJson);
    }
}
