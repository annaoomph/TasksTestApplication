package com.example.annakocheshkova.testapplication.utils.listener;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.error.ConnectionError;

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
     * @param baseError error that has happened
     */
    void onFailure(BaseError baseError);

}
