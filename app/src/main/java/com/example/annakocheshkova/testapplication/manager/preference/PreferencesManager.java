package com.example.annakocheshkova.testapplication.manager.preference;

/**
 * Manages all the preferences operations
 */
public interface PreferencesManager {

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
     * Sets the value of the expiration date in ms
     * @param date value to be set
     */
    void setExpirationDate(long date);

    /**
     * Gets the value of the expiration date
     * @return expiration date in ms
     */
    long getExpirationDate();

    /**
     * Get the value of max operation try attempts count
     * @return max try count
     */
    int getMaxOperationsTryCount();

    /**
     * Sets the value of max operation try attempts count
     * @param maxTry max try count
     */
    void setMaxOperationsTryCount(int maxTry);

    /**
     * Gets the value of interval between retries
     * @return interval in ms
     */
    long getIntervalBetweenRetries();

    /**
     * Sets the value of interval between retries
     * @param retryWait interval in ms
     */
    void setIntervalBetweenRetries(long retryWait);
}
