package com.example.annakocheshkova.testapplication.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the response sent by server on login
 */
public class LoginResponse extends BaseResponse {

    /**
     * Token for user further authorization
     */
    @SerializedName("token") private String token;

    /**
     * Token expiration date
     */
    @SerializedName("expirationDate") private String expirationDate;

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
    public String getExpirationDate() {
        return expirationDate;
    }
}
