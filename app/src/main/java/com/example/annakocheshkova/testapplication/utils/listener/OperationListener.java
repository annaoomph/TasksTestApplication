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
     * Called on successful request.
     * Be careful when updating ui in this method. You should always call activity.runOnUIThread or use Handler.
     * @param baseOperation instance of baseOperation to get the results
     */
    void onSuccess(T baseOperation);

    /**
     * Called on failure of request
     * Be careful when updating ui in this method. You should always call activity.runOnUIThread or use Handler.
     * @param connectionError defines what has happened
     */
    void onFailure(ConnectionError connectionError);
}
