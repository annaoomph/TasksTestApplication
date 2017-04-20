package com.example.annakocheshkova.testapplication.error;

import android.content.Context;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

/**
 * A basic class describing an error that can happen during the http request
 */
public class ConnectionError implements BaseError {

    /**
     * Code of the error
     */
    private int code;

    /**
     * Message coming with error (if it came, otherwise set to null - the error will load error string itself)
     */
    private String message;

    /**
     * Creates an instance of the error
     * @param code code of the error
     */
    public ConnectionError(int code) {
        this.code = code;
    }

    /**
     * Creates an instance of the error by the code and message
     * @param code code of the error
     * @param message message that came with the error
     */
    public ConnectionError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getErrorMessage() {
        if (message == null) {
            Context context = MyApplication.getAppContext();
            switch (code) {
                case 500:
                    return context.getString(R.string.error_message_server);
                case 401:
                    return context.getString(R.string.unauthorized_error);
                default:
                    return context.getString(R.string.connection_error);
            }
        } else {
            return message;
        }
    }
}
