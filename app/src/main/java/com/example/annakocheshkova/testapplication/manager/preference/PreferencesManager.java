package com.example.annakocheshkova.testapplication.manager.preference;

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
     * Gets a boolean from application preferences     *
     * @param prefName name of the preference
     * @return value of the preference
     */
    boolean getBoolean(String prefName);

    /**
     * Sets the boolean in the application preferences
     * @param prefName name of the preference
     * @param prefValue value of the preference
     */
    void putBoolean(String prefName, Boolean prefValue);

    /**
     * Sets the string in the application preferences
     * @param prefName name of the preference
     * @param prefValue value of the preference
     */
    void putString(String prefName, String prefValue);

    /**
     * Gets the string in the application preferences
     * @param prefName name of the preference
     * @return value of the preference
     */
    String getString(String prefName);

    /**
     * Gets an int value from application preferences
     * @param prefName name of the preference
     * @return value of the preference
     */
    int getInt(String prefName);

    /**
     * Sets the int value in the application preferences
     * @param prefName name of the preference
     * @param prefValue value of the preference
     */
    void putInt(String prefName, int prefValue);
}
