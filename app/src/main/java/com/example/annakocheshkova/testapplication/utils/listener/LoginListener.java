package com.example.annakocheshkova.testapplication.utils.listener;

/**
 * Listens to events connected with login
 */
public interface LoginListener {

    /**
     * Called when login was successful
     */
    void onSuccess();

    /**
     * Called when login failed
     * @param exception error that has happened
     */
    void onFailure(Exception exception);

}
