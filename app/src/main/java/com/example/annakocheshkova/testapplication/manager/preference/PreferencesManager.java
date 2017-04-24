package com.example.annakocheshkova.testapplication.manager.preference;

import java.util.Date;

/**
 * Manages all the preferences operations
 */
public interface PreferencesManager {

    /**
     * Name of the logged in boolean in the preferences
     */
    String LOGGED_IN = "loggedIn";

    /**
     * Name of the token string in the preferences
     */
    String TOKEN = "token";

    /**
     * Name of the expiration date preference
     */
    String EXPIRE = "expiration_date";

    /**
     * Name of the user id preference
     */
    String USER_ID = "user_id";

    /**
     * Sets the value of the logged in boolean
     * @param loggedIn value to be set
     */
    void setLoggedIn(boolean loggedIn);

    /**
     * Gets the value of the logged in boolean
      * @return value
     */
    boolean getLoggedIn();

    /**
     * Sets the value of the token string
     * @param token value to be set
     */
    void setToken(String token);

    /**
     * Gets the value of the token string
     * @return value
     */
    String getToken();

    /**
     * Sets the value of the use id integer
     * @param id value to be set
     */
    void setUserId(int id);

    /**
     * Gets the value of the user id integer
     * @return value
     */
    int getUserId();

    /**
     * Sets the value of the expiration date string
     * @param date value to be set
     */
    void setExpirationDate(String date);

    /**
     * Gets the value of the expiration date string and returns it converted to date format
     * @return expiration date
     */
    Date getExpirationDate();
}
