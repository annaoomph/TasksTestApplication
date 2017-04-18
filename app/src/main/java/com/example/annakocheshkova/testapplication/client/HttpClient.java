package com.example.annakocheshkova.testapplication.client;

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

/**
 * A client making http calls
 */
class HttpClient implements BaseHttpClient{

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
    HttpClient(HttpListener httpListener) {
        this.httpListener = httpListener;
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        token = preferencesManager.getString(PreferencesManager.TOKEN);
    }

    @Override
    public void doGetRequest(String url){
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

    @Override
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
