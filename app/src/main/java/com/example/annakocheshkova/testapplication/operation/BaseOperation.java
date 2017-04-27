package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.NetworkUtil;
import com.example.annakocheshkova.testapplication.utils.exception.BadResponseException;
import com.example.annakocheshkova.testapplication.utils.exception.ConnectionException;
import com.example.annakocheshkova.testapplication.utils.exception.NoInternetException;
import com.example.annakocheshkova.testapplication.utils.exception.NotImplementedException;
import com.google.gson.JsonParseException;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
     * Exception that has been thrown if there was an error
     */
    private Exception exception;

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
        try {
            Response response = getResponse();
            String responseJson = retrieveDataFromResponse(response);
            handleResponse(responseJson);
        } catch (BadResponseException e) {
            exception = e;
        } catch (IllegalArgumentException | IOException | JsonParseException e) {
            if (!NetworkUtil.isNetworkAvailable()) {
                exception = new NoInternetException();
            } else {
                exception = new ConnectionException(url, e);
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the response whether fake or real
     * @return response
     * @throws IllegalArgumentException exception if one of the arguments is not valid
     * @throws IOException exception during making a request
     */
    Response getResponse() throws IllegalArgumentException, IOException {
        boolean fakeRequest = ConfigurationManager.getFakeRequest();
        if (fakeRequest) {
            String json = getFakeResponseJson();
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("http://example.com")
                    .build();
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();
            return new Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(HttpClient.MEDIA_TYPE_JSON, json))
                    .code(200).build();
        } else {
            HttpClient httpClient = new HttpClient();
            switch (getRequestType()) {
                case GET:
                    return httpClient.doGetRequest(url, prepareGetContent());
                case POST:
                    return httpClient.doPostRequest(url, preparePostContent());
                default:
                    throw new RuntimeException(new NotImplementedException(getRequestType().toString()));
            }
        }
    }

    /**
     * Tries to get the response body string
     * @param response response itself
     * @return response body string
     * @throws IOException exception during reading body
     * @throws BadResponseException exception if the response is not successful
     */
    private String retrieveDataFromResponse(Response response) throws IOException, BadResponseException {
        if (!response.isSuccessful()) {
            throw new BadResponseException(response.code(), response.message());
        } else {
            return response.body().string();
        }
    }

    /**
     * Takes the response string, parses it and calls the listener with an error or the given response
     * @param responseJson json response string
     * @throws JsonParseException Exception during parsing the json string
     * @throws BadResponseException exception if the response is not successful
     */
    private void handleResponse(String responseJson) throws JsonParseException, BadResponseException {
        parseResponse(responseJson);
        BaseResponse baseResponse = getBaseResponse();
        if (baseResponse.getCode() != 200) {
            throw new BadResponseException(baseResponse.getCode(), baseResponse.getMessage());
        }
    }

    /**
     * Gets the exception
     * @return exception
     */
    public Exception getException() {
        return exception;
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
     * Prepares fake data
     */
    abstract String getFakeResponseJson();
}
