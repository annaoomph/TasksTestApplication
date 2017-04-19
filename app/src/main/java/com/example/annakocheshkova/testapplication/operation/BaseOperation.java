package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.client.BaseHttpClient;
import com.example.annakocheshkova.testapplication.client.HttpClientFactory;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.HttpCallback;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Base operation for handling http requests
 * @param <T> type of data
 */
public class BaseOperation<T> implements HttpCallback {

    /**
     * Url to maje requests to
     */
    private String url;

    /**
     * Response returned by the request
     */
    private String requestResponse;

    /**
     * Converter to prepare data
     */
    Converter<T> converter;

    Converter<BaseResponse> responseConverter;

    BaseResponse responseObject;
    /**
     * A listener of request events
     */
    OperationListener operationListener;

    /**
     * Creates an instance of BaseOperation
     * @param url url to make requests to
     */
    BaseOperation(String url, Converter<T> converter, OperationListener operationListener) {
        this.operationListener = operationListener;
        this.url = url;
        this.converter = converter;
    }

    /**
     * Prepares the content for sending it to the client.
     * Base operation doesn't require to have content, so the method should be overridden in those inheritors that use it.
     * @return RequestBody with formatted content
     */
    RequestBody prepareContent() {
        return null;
    }

    /**
     * Makes the request
     */
    public void execute() {
        BaseHttpClient httpClient = HttpClientFactory.getHttpClient();
        RequestBody content = prepareContent();
        if (content == null) {
            httpClient.doGetRequest(url, this);
        } else {
            httpClient.doPostRequest(url, prepareContent(), this);
        }
    }

    /**
     * Gets the response body
     * @return full response body
     */
    String getResponse() {
        return this.requestResponse;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        operationListener.onFailure(new ConnectionError(500));
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
            operationListener.onFailure(new ConnectionError(response.code()));
        } else {
            requestResponse = response.body().string();
            responseObject = responseConverter.deconvertObject(requestResponse, BaseResponse.class);
            operationListener.onSuccess(this);
        }
    }

    @Override
    public void onFakeResponse() {

    }
}
