package com.example.annakocheshkova.testapplication.utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A client making http calls
 */
public class HttpClient {

    /**
     * A listener of http events
     */
    private Callback httpListener;

    /**
     * Http client
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Content media type
     */
    private static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * Creates an instance of HttpClient
     * @param httpListener listener of http events
     */
    public HttpClient(Callback httpListener) {
        this.httpListener = httpListener;
    }

    /**
     * Makes a GET-request
     * @param url to visit
     */
    public void doGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                //.addHeader("Accept", "application/json; q=0.5")
                //TODO add headers (token)
                .build();
        client.newCall(request).enqueue(httpListener);
    }

    /**
     * Makes a POST-request
     * @param url url to make a request
     * @param data to be sent
     */
    public void doPostRequest(String url, String data){
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_JSON, data))
                .build();
        client.newCall(request).enqueue(httpListener);
    }
}
