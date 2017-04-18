package com.example.annakocheshkova.testapplication.utils.error;

import android.content.Context;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

/**
 * A basic class describing an error that can happen during the http request
 */
public class ConnectionError implements BaseError {

    /**
     * Code of the response
     */
    private int code;

    /**
     * Creates an instance of the error
     * @param code code of the error
     */
    public ConnectionError(int code) {
        this.code = code;
    }

    @Override
    public String getErrorMessage() {
        Context context = MyApplication.getAppContext();
        switch(code) {
            case 500: return context.getString(R.string.error_message_server);
            case 401: return context.getString(R.string.unauthorized_error);
            default: return context.getString(R.string.connection_error);
        }
    }
}
