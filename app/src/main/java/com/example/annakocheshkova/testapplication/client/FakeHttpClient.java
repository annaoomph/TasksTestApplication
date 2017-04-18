package com.example.annakocheshkova.testapplication.client;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * An http-client making fake requests to server (used if server is temporary unavailable or simply doesn't exist
 */
class FakeHttpClient implements BaseHttpClient {

    @Override
    public void doGetRequest(String url, Callback callback) {
        try {
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("example.com")
                    .build();

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();

            Response okHttpResponse = new Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(BaseHttpClient.MEDIA_TYPE_JSON, ""))
                    .code(200).build();
            callback.onResponse(null, okHttpResponse);
        } catch (IOException e) {}
    }

    @Override
    public void doPostRequest(String url, String data, MediaType mediaType, Callback callback) {
        try {
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("example.com")
                    .build();

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();

            Response okHttpResponse = new Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(mediaType, data))
                    .code(200).build();
            callback.onResponse(null, okHttpResponse);
        } catch (IOException e) {}
    }
}
