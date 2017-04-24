package com.example.annakocheshkova.testapplication.utils.listener;

import android.support.annotation.UiThread;

import com.example.annakocheshkova.testapplication.operation.BaseOperation;
import com.example.annakocheshkova.testapplication.error.ConnectionError;

/**
 * A basic listener for operations events
 * @param <T> type of operation
 */
public interface OperationListener<T extends BaseOperation> {

    /**
     * Called on successful request
     * @param baseOperation instance of baseOperation to get the results
     */
    @UiThread
    void onSuccess(T baseOperation);

    /**
     * Called on failure of request
     * @param connectionError defines what has happened
     */
    @UiThread
    void onFailure(ConnectionError connectionError);
}
