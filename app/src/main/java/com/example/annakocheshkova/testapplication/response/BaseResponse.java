package com.example.annakocheshkova.testapplication.response;

import com.google.gson.annotations.SerializedName;

/**
 * Basic Response sent by server
 */
public class BaseResponse {

    /**
     * Code of the response
     */
    @SerializedName("code") private int code;

    /**
     * Message of error, if there is
     */
    @SerializedName("message") private String message;

    /**
     * Gets the value of code and returns it     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the value of message and returns it     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

}
