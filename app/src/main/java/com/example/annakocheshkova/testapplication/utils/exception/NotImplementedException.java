package com.example.annakocheshkova.testapplication.utils.exception;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

/**
 * A custom exception type for the places where the feature has not yet been implemented, but called
 */
public class NotImplementedException extends Exception {

    /**
     * Name of the called feature
     */
    private String name;

    /**
     * Creates a new instance of the exception
     * @param name name of the feature
     */
    public NotImplementedException(String name) {
        this.name = name;
    }

    /**
     * Gets the message to be shown to user
     * @return message
     */
    public String getMessage() {
        return String.format(MyApplication.getAppContext().getString(R.string.feature_not_implemented), name);
    }

}
