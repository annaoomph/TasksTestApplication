package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.error.FileError;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.NetworkUtil;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.google.gson.JsonParseException;

import java.io.IOException;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Base operation that makes http request
 */
abstract class BaseOperation {

    /**
     * Enum with request types
     */
    enum RequestType {
        POST,
        GET
    }

    /**
     * Url to make requests to
     */
    private String url;

    /**
     * Error that has been thrown if there was an error
     */
    private BaseError error;

    /**
     * Creates an instance of BaseOperation
     * @param url url to make requests to
     */
    BaseOperation(String url) {
        this.url = url;
    }

    /**
     * Prepares the content for sending it to the client.
     * @return RequestBody with formatted content
     */
    abstract RequestBody preparePostContent();

    /**
     * Prepares the content for sending it to the client by get-method
     * @return String with additional parameters to url
     */
    abstract String prepareGetContent();

    /**
     * Makes the request itself
     * @return true if the operation has been successfully executed, false otherwise
     */
    boolean execute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean fakeRequest = ConfigurationManager.getFakeRequest();
        if (fakeRequest) {
            return onFakeResponse();
        } else {
            HttpClient httpClient = new HttpClient();
            try {
                switch (getRequestType()) {
                    case GET:
                        return onResponse(httpClient.doGetRequest(url, prepareGetContent()));
                    case POST:
                        return onResponse(httpClient.doPostRequest(url, preparePostContent()));
                    default:
                        throw new RuntimeException(new NotImplementedException(getRequestType().toString()));
                }
            } catch (IllegalArgumentException e) {
                //TODO Ask if this is right
                if (NetworkUtil.isNetworkAvailable()) {
                    error = new ConnectionError(e.getMessage());
                } else {
                    error = new ConnectionError(ConnectionError.ConnectionErrorType.CONNECTION_ERROR);
                }
                return false;
            }
            catch (IOException e) {
                error = new ConnectionError(e.getMessage());
                return false;
            }
        }
    }

    /**
     * Gets the error
     * @return error
     */
    BaseError getError() {
        return error;
    }

    /**
     * Called if response has been delivered
     * @param response response from the server
     * @throws IOException possible exception
     * @return true if response is successful, false otherwise
     */
    private boolean onResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            error = new ConnectionError(response.code());
            return false;
        } else {
            return handleResponse(response.body().string());
        }
    }

    /**
     * Takes the response string, parses it and calls the listener with an error or the given response
     * @param responseJson json response string
     * @return true if parsed response is successful, false otherwise
     */
    boolean handleResponse(String responseJson) {
        try {
            parseResponse(responseJson);
            BaseResponse baseResponse = getBaseResponse();
            if (baseResponse.getCode() != 200) {
                error = new ConnectionError(baseResponse.getCode(), baseResponse.getMessage());
                return false;
            } else {
                return true;
            }
        } catch (JsonParseException exception) {
            error = new FileError(FileError.FileErrorType.PARSE_ERROR);
            return false;
        }
    }

    /**
     * Parses json and sets the response object
     * @param responseJson json to be parsed
     * @throws JsonParseException exception during parsing the json
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
    public abstract boolean onFakeResponse();
}
