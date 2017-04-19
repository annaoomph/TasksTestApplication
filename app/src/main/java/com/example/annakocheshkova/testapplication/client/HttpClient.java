package com.example.annakocheshkova.testapplication.client;

import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.utils.listener.HttpCallback;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A client making http calls
 */
class HttpClient implements BaseHttpClient {

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
    HttpClient() {
        preferencesManager = PreferencesFactory.getPreferencesManager();
    }

    @Override
    public void doGetRequest(String url, HttpCallback callback) {
        String token = preferencesManager.getString(PreferencesManager.TOKEN);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .build();
        client.newCall(request).enqueue(callback);
        
    }

    @Override
    public void doPostRequest(String url, RequestBody data, HttpCallback callback) {
        String token = preferencesManager.getString(PreferencesManager.TOKEN);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .post(data)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
