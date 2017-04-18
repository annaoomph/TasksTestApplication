package com.example.annakocheshkova.testapplication.client;

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
    public void doGetRequest(String url, Callback callback) {
        String token = preferencesManager.getString(PreferencesManager.TOKEN);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void doPostRequest(String url, String data, MediaType mediaType, Callback callback) {
        String token = preferencesManager.getString(PreferencesManager.TOKEN);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .post(RequestBody.create(mediaType, data))
                .build();
        client.newCall(request).enqueue(callback);
    }
}
