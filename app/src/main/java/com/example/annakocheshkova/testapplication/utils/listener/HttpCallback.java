package com.example.annakocheshkova.testapplication.utils.listener;

import okhttp3.Callback;

/**
 * A custom callback based on okHttp callback
 */
public interface HttpCallback extends Callback{

    /**
     * Called when fake response successfully executed
     */
    void onFakeResponse();
}
