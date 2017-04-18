package com.example.annakocheshkova.testapplication.client;

import okhttp3.Callback;
import okhttp3.MediaType;

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
    void doGetRequest(String url, Callback callback);

    /**
     * Makes a post-request
     * @param url url to make request to
     * @param data data to be sent
     * @param mediaType type of the content
     * @param callback a callback for responding to client events
     */
    void doPostRequest(String url, String data, MediaType mediaType, Callback callback);
}
