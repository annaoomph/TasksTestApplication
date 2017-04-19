package com.example.annakocheshkova.testapplication.response;

/**
 * Basic Response sent by server
 */
public class BaseResponse {

    /**
     * Token string
     */
    private String token;

    /**
     * Date of token expiration
     */
    private long expirationDate;

    /**
     * Items (if present)
     */
    private String items;

    public BaseResponse(String token, long expirationDate, String items) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.items = items;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public String getToken() {
        return token;
    }

    public String getItems() {
        return items;
    }
}
