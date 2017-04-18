package com.example.annakocheshkova.testapplication.client;

import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;

/**
 * A factory that returns an http client
 */
public class HttpClientFactory {

    /**
     * Gets an http-client depending on the situation
     * @return http-client
     */
    public static BaseHttpClient getHttpClient() {
        boolean fakeRequest = ConfigurationManager.getFakeRequest();
        if (fakeRequest) {
            return new FakeHttpClient();
        } else {
            return new HttpClient();
        }
    }
}
