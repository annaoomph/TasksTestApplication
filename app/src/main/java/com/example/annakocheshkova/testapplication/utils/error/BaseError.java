package com.example.annakocheshkova.testapplication.utils.error;

/**
 * An interface for different types of errors
 */
public interface BaseError {

    /**
     * Gets the message depending on the type of the error
     * @return error message to be shown
     */
    String getErrorMessage();
}
