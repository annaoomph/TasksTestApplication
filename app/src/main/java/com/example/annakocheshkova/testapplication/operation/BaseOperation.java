package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.client.BaseHttpClient;
import com.example.annakocheshkova.testapplication.client.HttpClientFactory;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Base operation for handling http requests
 * @param <T> type of data
 */
public class BaseOperation<T> implements Callback {

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

    /**
     * A listener of request events
     */
    private OperationListener operationListener;

    /**
     * Creates an instance of BaseOperation
     * @param url url to make requests to
     */
    BaseOperation(String url, Converter<T> converter) {
        this.url = url;
        this.converter = converter;
    }

    /**
     * Prepares the content for sending it to the client.
     * Base operation doesn't require to have content, so the method should be overridden in those inheritors that use it.
     * @return String with formatted content
     */
    String prepareContent() {
        return "";
    }

    /**
     * Makes the post request
     * @param operationListener listens to request events
     */
    public void executePost(final OperationListener operationListener) {
        this.operationListener = operationListener;
        BaseHttpClient httpClient = HttpClientFactory.getHttpClient();
        httpClient.doPostRequest(url, prepareContent(), BaseHttpClient.MEDIA_TYPE_JSON, this);
    }

    /**
     * Makes the get request
     * @param operationListener listens to request events
     */
    public void executeGet(final OperationListener operationListener) {
        this.operationListener = operationListener;
        BaseHttpClient httpClient = HttpClientFactory.getHttpClient();
        httpClient.doGetRequest(url, this);
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
            requestResponse =  response.body().string();
            operationListener.onSuccess(this);
        }
    }
}
