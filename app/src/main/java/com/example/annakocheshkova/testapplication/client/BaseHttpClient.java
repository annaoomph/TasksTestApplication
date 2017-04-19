package com.example.annakocheshkova.testapplication.client;

import com.example.annakocheshkova.testapplication.utils.listener.HttpCallback;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * A basic interface for http clients
 */
public interface BaseHttpClient {

    /**
     * Content media type
     */
    MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Makes a get-request
     * @param url url to make request to
     * @param callback a callback for responding to client events
     */
    void doGetRequest(String url, HttpCallback callback);

    /**
     * Makes a post-request
     * @param url url to make request to
     * @param data data to be sent
     * @param callback a callback for responding to client events
     */
    void doPostRequest(String url, RequestBody data, HttpCallback callback);
}
