package com.example.annakocheshkova.testapplication.error;

import android.content.Context;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

/**
 * A basic class describing an error that can happen during the http request
 */
public class ConnectionError implements BaseError {

    /**
     * An enum with possible connection error types
     */
    public enum ConnectionErrorType {
        CONNECTION_ERROR,
        SERVER_ERROR,
        UNAUTHORIZED_ERROR
    }

    /**
     * Type of the error
     */
    private ConnectionErrorType type;

    /**
     * Message coming with error (if it came, otherwise set to null - the error will load error string itself)
     */
    private String message;

    /**
     * Creates an instance of the error
     * @param code code of the error
     */
    public ConnectionError(int code) {
        switch (code) {
            case 500:
                this.type = ConnectionErrorType.SERVER_ERROR; break;
            case 401:
                this.type = ConnectionErrorType.UNAUTHORIZED_ERROR; break;
            default:
                this.type = ConnectionErrorType.CONNECTION_ERROR; break;
        }
    }

    /**
     * Creates an instance of the error
     * @param type type of the error
     */
    public ConnectionError(ConnectionErrorType type) {
        this.type = type;
    }

    /**
     * Creates an instance of the error by the code and message
     * @param message message that came with the error
     */
    public ConnectionError(String message) {
        this.type = ConnectionErrorType.SERVER_ERROR;
        this.message = message;
    }

    /**
     * Creates an instance of the error by the code and message
     * @param code code of the error
     * @param message message that came with the error
     */
    public ConnectionError(int code, String message) {
        this(code);
        this.message = message;
    }

    @Override
    public String getErrorMessage() {
        if (message == null) {
            Context context = MyApplication.getAppContext();
            switch (type) {
                case SERVER_ERROR:
                    return context.getString(R.string.error_message_server);
                case UNAUTHORIZED_ERROR:
                    return context.getString(R.string.unauthorized_error);
                case CONNECTION_ERROR:
                    context.getString(R.string.connection_error);
                default:
                    throw new RuntimeException(new NotImplementedException(type.toString()));
            }
        } else {
            return message;
        }
    }
}
