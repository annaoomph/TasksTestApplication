package com.example.annakocheshkova.testapplication.operation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.error.FileError;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Base operation for handling http requests
 */
public abstract class BaseOperation {

    /**
     * Enum with request types
     */
    enum RequestType {
        POST,
        GET
    }

    /**
     * Max count of retries for one operation
     */
    private static final int maxRetry = 1;

    /**
     * A time (in ms) to wait before another retry
     */
    private static final long retryWait = 5000;

    /**
     * Current amount of made retries
     */
    private int retryCount;

    /**
     * A listener of request events
     */
    private OperationListener operationListener;

    /**
     * Url to maje requests to
     */
    private String url;

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
    abstract RequestBody preparePostContent();

    /**
     * Prepares the content for sending it to the client by get-method
     * @return String with parameters to url
     */
    abstract String prepareGetContent();

    /**
     * Makes the request
     */
    void execute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean fakeRequest = ConfigurationManager.getFakeRequest();
        if (fakeRequest) {
            onFakeResponse();
        } else {
            HttpClient httpClient = new HttpClient();
            try {
                switch (getRequestType()) {
                    case GET:
                        onResponse(httpClient.doGetRequest(url, prepareGetContent()));
                        break;
                    case POST:
                        onResponse(httpClient.doPostRequest(url, preparePostContent()));
                        break;
                    default:
                        throw new RuntimeException(new NotImplementedException(getRequestType().toString()));
                }
            } catch (IllegalArgumentException e) {
                operationListener.onFailure(new ConnectionError(e.getMessage()));
            }
            catch (IOException e) {
                if (!isNetworkAvailable()) {
                    retry();
                } else {
                    operationListener.onFailure(new ConnectionError(e.getMessage()));
                }
            }
        }
    }

    /**
     * Retries to execute the operation after the given time
     */
    private void retry() {
        if (retryCount >= maxRetry) {
            operationListener.onFailure(new ConnectionError(ConnectionError.ConnectionErrorType.CONNECTION_ERROR));
        } else {
            retryCount++;
            try {
                Thread.sleep(retryWait);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            execute();
        }
    }

    /**
     * Finds out if the network is available
     * @return true if yes, false otherwise
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Called if response if delivered
     * @param response response from the server
     * @throws IOException parse exception
     */
    private void onResponse(Response response) throws IOException {
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
        try {
            parseResponse(responseJson);
            BaseResponse baseResponse = getBaseResponse();
            if (baseResponse.getCode() != 200) {
                operationListener.onFailure(new ConnectionError(baseResponse.getCode(), baseResponse.getMessage()));
            } else {
                operationListener.onSuccess(this);
            }
        } catch (JsonParseException exception) {
            operationListener.onFailure(new FileError(FileError.FileErrorType.PARSE_ERROR));
        }
    }

    /**
     * Parses json and sets the response object
     * @param responseJson json to be parsed
     */
    abstract void parseResponse(String responseJson) throws JsonParseException;

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
