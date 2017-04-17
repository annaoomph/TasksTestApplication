package com.example.annakocheshkova.testapplication.utils.Listener;

/**
 * A custom listener responding to events connected with request
 */
public interface HttpListener {

    /**
     * Called if the request has been successful
     * @param response response body
     */
    void onSuccess(String response);

    /**
     * Called if the request has failed
     */
    void onFailure();

    /**
     * Called if the request has failed because user is not authorized or entered wrong credentials
     */
    void onUnauthorized();
}
