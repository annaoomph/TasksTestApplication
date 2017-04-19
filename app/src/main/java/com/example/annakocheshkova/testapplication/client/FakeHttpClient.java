package com.example.annakocheshkova.testapplication.client;

import com.example.annakocheshkova.testapplication.utils.listener.HttpCallback;

import okhttp3.RequestBody;

/**
 * An http-client making fake requests to server (used if server is temporary unavailable or simply doesn't exist
 */
class FakeHttpClient implements BaseHttpClient {

    @Override
    public void doGetRequest(String url, HttpCallback callback) {
        callback.onFakeResponse();
    }

    @Override
    public void doPostRequest(String url, RequestBody data, HttpCallback callback) {
        callback.onFakeResponse();
    }
}
