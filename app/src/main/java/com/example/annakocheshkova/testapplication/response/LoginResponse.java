package com.example.annakocheshkova.testapplication.response;

import com.example.annakocheshkova.testapplication.utils.DateParser;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

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
     * User name
     */
    @SerializedName("userName") private String userName;

    /**
     * User id
     */
    @SerializedName("userId") private int userId;

    /**
     * Gets the value of token and returns it
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the value of userId and returns it
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the value of expiration date and returns it
     * @return expiration date in ms

     */
    public Date getExpirationDate() {
        return DateParser.parse(expirationDate);
    }

    /**
     * Gets the value of user name and returns it
     * @return user name
     */
    public String getUserName() {
        return userName;
    }
}
