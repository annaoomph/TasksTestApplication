package com.example.annakocheshkova.testapplication.manager.preference;

import java.util.Date;
import java.util.Set;

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
    String EXPORT_DATES = "export_dates";

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
     * Add the export date
     * @param date date to be saved
     */
    void addExportDate(String date);

    /**
     * Gets the list of export dates
     * @return values
     */
    Set<String> getExportDates();

    /**
     * Sets the value of the expiration date string
     * @param date value to be set
     */
    void setExpirationDate(String date);

    /**
     * Gets the value of the expiration date string and returns it
     * @return expiration date
     */
    String getExpirationDate();
}
