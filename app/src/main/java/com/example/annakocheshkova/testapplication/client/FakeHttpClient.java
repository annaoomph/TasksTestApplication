package com.example.annakocheshkova.testapplication.client;

import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;

/**
 * An http-client making fake requests to server (used if server is temporary unavailable or simply doesn't exist
 */
class FakeHttpClient implements BaseHttpClient{

    /**
     * A listener of http events
     */
    private HttpListener httpListener;

    /**
     * Creates an instance of http client
     * @param httpListener listener on http events
     */
    FakeHttpClient(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void doGetRequest(String url) {
        httpListener.onSuccess("");
    }

    @Override
    public void doPostRequest(String url, String data) {
        httpListener.onSuccess("");
    }
}
