package com.example.annakocheshkova.testapplication.utils;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A client making http calls
 */
public class HttpClient {

    /**
     * Content media type
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Preferences manager instance
     */
    private PreferencesManager preferencesManager;

    /**
     * Http client
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Creates an instance of HttpClient
     */
    public HttpClient() {
        preferencesManager = PreferencesFactory.getPreferencesManager();
    }

    /**
     * Makes a GET-request
     * @param url url to make request to
     * @param params params to url
     */
    public Response doGetRequest(String url, String params) throws IOException {
        String token = preferencesManager.getToken();
        Request request = new Request.Builder()
                .url(url + params)
                .addHeader("Authentication", token)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Makes a POST-request
     * @param url url to make request to
     * @param data data to be sent
     */
    public Response doPostRequest(String url, RequestBody data) throws IOException{
        String token = preferencesManager.getToken();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .post(data)
                .build();
        return client.newCall(request).execute();
    }
}
