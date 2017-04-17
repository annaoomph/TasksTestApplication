package com.example.annakocheshkova.testapplication.utils;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A client making http calls
 */
public class HttpClient {

    /**
     * A listener of http events
     */
    private HttpListener httpListener;

    /**
     * A token for authorization
     */
    private String token;

    /**
     * Http client
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Content media type
     */
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Creates an instance of HttpClient
     * @param httpListener listener of http events
     */
    public HttpClient(HttpListener httpListener) {
        this.httpListener = httpListener;
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        token = preferencesManager.getString(MyApplication.getAppContext().getString(R.string.token_pref_name));
    }

    /**
     * Makes a GET-request
     * @param url to visit
     */
    public void doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .build();
        client.newCall(request).enqueue(new Callback()  {

            @Override
            public void onFailure(Call call, IOException e) {
                httpListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (response.code() == 401) {
                        httpListener.onUnauthorized();
                    } else {
                        httpListener.onFailure();
                    }
                } else {
                    httpListener.onSuccess(response.body().toString());
                }
            }
        });
    }

    /**
     * Makes a fake request
     * @param url to visit
     */
    public void doFakeRequest(String url) {
       httpListener.onSuccess("");
    }

    /**
     * Makes a POST-request
     * @param url url to make a request
     * @param data to be sent
     */
    public void doPostRequest(String url, String data) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authentication", token)
                .post(RequestBody.create(MEDIA_TYPE_JSON, data))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (response.code() == 401) {
                        httpListener.onUnauthorized();
                    } else {
                        httpListener.onFailure();
                    }
                } else {
                    httpListener.onSuccess(response.body().toString());
                }
            }
        });
    }
}
