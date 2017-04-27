package com.example.annakocheshkova.testapplication.utils.exception;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

/**
 * Created by anna.kocheshkova on 4/27/2017.
 */

public class ConnectionException extends Exception {

    private String url;

    public ConnectionException(String url, Exception e) {
        super(e.getMessage(), e.getCause());
        this.url = url;
    }

    /**
     * Gets the message
     * @return message
     */
    @Override
    public String getMessage() {
        return url + "\n" + super.getMessage();
    }
}
