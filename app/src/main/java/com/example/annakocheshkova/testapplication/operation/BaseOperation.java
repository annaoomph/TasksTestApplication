package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Base operation for handling http requests
 */
public abstract class BaseOperation implements Callback {

    /**
     * Enum with request types
     */
    enum RequestType {
        POST,
        GET
    }

    /**
     * Url to maje requests to
     */
    private String url;

    /**
     * A listener of request events
     */
    private OperationListener operationListener;

    /**
     * Creates an instance of BaseOperation
     * @param url url to make requests to
     */
    BaseOperation(String url, OperationListener operationListener) {
        this.operationListener = operationListener;
        this.url = url;
    }

    /**
     * Prepares the content for sending it to the client.
     * @return RequestBody with formatted content
     */
    abstract RequestBody prepareContent();

    /**
     * Makes the request
     */
    void execute() {
        boolean fakeRequest = ConfigurationManager.getFakeRequest();
        if (fakeRequest) {
            onFakeResponse();
        } else {
            HttpClient httpClient = new HttpClient();
            switch (getRequestType()) {
                case GET:
                    httpClient.doGetRequest(url, this);
                case POST:
                    httpClient.doPostRequest(url, prepareContent(), this);
                default:
                    throw new RuntimeException(new NotImplementedException(getRequestType().toString()));
            }
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        operationListener.onFailure(new ConnectionError(500, e.getMessage()));
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
            operationListener.onFailure(new ConnectionError(response.code()));
        } else {
            handleResponse(response.body().string());
        }
    }

    /**
     * Takes the response string and responds depending on the information sent by the server
     * @param responseJson json response string
     */
    void handleResponse(String responseJson) {
        parseResponse(responseJson);
        BaseResponse baseResponse = getBaseResponse();
        if (baseResponse.getCode() != 200) {
            operationListener.onFailure(new ConnectionError(baseResponse.getCode(), baseResponse.getMessage()));
        } else {
            operationListener.onSuccess(this);
        }
    }

    /**
     * Parses json and sets the response object
     * @param responseJson json to be parsed
     */
    abstract void parseResponse(String responseJson);

    /**
     * Gets the response in base format
     * @return response
     */
    abstract BaseResponse getBaseResponse();

    /**
     * Gets the request type
     * @return type of the request
     */
    abstract RequestType getRequestType();

    /**
     * Sends fake data
     */
    public abstract void onFakeResponse();
}
