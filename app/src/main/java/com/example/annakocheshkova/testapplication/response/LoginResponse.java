package com.example.annakocheshkova.testapplication.response;

import com.google.gson.Gson;

/**
 * Represents the response sent by server on login
 */
public class LoginResponse extends BaseResponse {

    /**
     * Token for user further authorization
     */
    private String token;

    /**
     * Token expiration date
     */
    private long expirationDate;

    /**
     * Gets the value of token and returns it     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the value of expiration date and returns it     *
     * @return expiration date
     */
    public long getExpirationDate() {
        return expirationDate;
    }
}
