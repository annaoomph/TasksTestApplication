package com.example.annakocheshkova.testapplication.utils.listener;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.response.BaseResponse;

/**
 * A basic listener for operations events
 * @param <T> type of operation
 */
public interface OperationListener<T extends BaseResponse> {

    /**
     * Called on successful request.
     * Be careful when updating ui in this method. You should always call activity.runOnUIThread or use Handler.
     * @param baseResponse instance of baseResponse to get the results
     */
    void onSuccess(T baseResponse);

    /**
     * Called on failure of request
     * Be careful when updating ui in this method. You should always call activity.runOnUIThread or use Handler.
     * @param baseError defines what has happened
     */
    void onFailure(BaseError baseError);
}
