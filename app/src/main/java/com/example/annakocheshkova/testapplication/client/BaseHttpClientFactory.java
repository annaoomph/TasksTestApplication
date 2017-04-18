package com.example.annakocheshkova.testapplication.client;

import com.example.annakocheshkova.testapplication.utils.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.utils.listener.HttpListener;

import java.io.IOException;

/**
 * A factory that returns an http client
 */
public class BaseHttpClientFactory {

    /**
     * Gets an http-client depending on the situation
     * @param httpListener http listener for creating a client
     * @return http-client
     */
    public static BaseHttpClient getHttpClient(HttpListener httpListener) throws IOException {
        String fakeRequestString = ConfigurationManager.getConfigValue(ConfigurationManager.FAKE_REQUEST);
        if (fakeRequestString == null) {
            throw new IOException();
        }
        boolean fakeRequest = fakeRequestString.equalsIgnoreCase("true");
        if (fakeRequest) {
            return new FakeHttpClient(httpListener);
        } else {
            return new HttpClient(httpListener);
        }
    }
}
