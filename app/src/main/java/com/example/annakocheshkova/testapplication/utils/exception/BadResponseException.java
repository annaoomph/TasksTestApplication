package com.example.annakocheshkova.testapplication.utils.exception;

/**
 * Exception during making http request
 */
public class BadResponseException extends Exception {

    /**
     * Code of the http response
     */
    private int code;

    /**
     * Creates an instance of the exception
     * @param code code of the response
     * @param message message to be shown
     */
    public BadResponseException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Creates an instance of the exception
     */
    public BadResponseException() {
        super();
    }

    /**
     * Gets the message
     * @return message
     */
    @Override
    public String getMessage() {
        return code + "\n" + super.getMessage();
    }

    /**
    * Gets the exception code
     * @return code
     */
    public int getCode() {
        return code;
    }
}
