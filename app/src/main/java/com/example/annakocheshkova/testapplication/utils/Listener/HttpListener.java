package com.example.annakocheshkova.testapplication.utils.Listener;

import java.io.IOException;

/**
 * Listens to http events
 */
public interface HttpListener {

    /**
     * Called when some failure during making http request has happened
     */
    void onFailure() throws IOException;

    /**
     * Called when an http request has completed successfully
     * @param response http response
     */
    void onSuccess(String response);
}
