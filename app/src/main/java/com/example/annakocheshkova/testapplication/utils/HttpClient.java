package com.example.annakocheshkova.testapplication.utils;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;

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
     * @param callback listens to http events
     */
    public void doGetRequest(String url, String params, Callback callback) {
        String token = preferencesManager.getString(PreferencesManager.TOKEN);
        Request request = new Request.Builder()
                .url(url + params)
                .addHeader("Authentication", token)
                .build();
        client.newCall(request).enqueue(callback);
        
    }

    /**
     * Makes a POST-request
     * @param url url to make request to
     * @param data data to be sent
     * @param callback listens to http events
     */
    public void doPostRequest(String url, RequestBody data, Callback callback) {
        String token = preferencesManager.getString(PreferencesManager.TOKEN);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .post(data)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
