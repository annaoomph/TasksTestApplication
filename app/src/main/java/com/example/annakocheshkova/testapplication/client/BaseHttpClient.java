package com.example.annakocheshkova.testapplication.client;

/**
 * A basic interface for http clients
 */
public interface BaseHttpClient {

    /**
     * Makes a get-request
     * @param url url to make request to
     */
    void doGetRequest(String url);

    /**
     * Makes a post-request
     * @param url url to make request to
     * @param data data to be sent
     */
    void doPostRequest(String url, String data);
}
