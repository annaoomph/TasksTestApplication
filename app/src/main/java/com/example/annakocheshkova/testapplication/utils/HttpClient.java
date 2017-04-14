package com.example.annakocheshkova.testapplication.utils;

import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;

import java.io.IOException;
import java.util.List;

/**
 * A client making http calls
 */
public class HttpClient {

    /**
     * A listener of http events
     */
    private HttpListener httpListener;

    /**
     * Creates an instance of HttpClient
     * @param httpListener listener of http events
     */
    public HttpClient(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    /**
     * Makes a GET-request
     * @param url to visit
     */
    public void doGetRequest(String url) throws IOException{

    }

    /**
     * Makes a POST-request
     * @param url url to make a request
     * @param data to be sent
     */
    public void doPostRequest(String url, String data) throws IOException{
        httpListener.onSuccess("");

    }
}
