package com.example.annakocheshkova.testapplication.utils.preference;

/**
 * Manages all the preferences operations
 */
public interface PreferencesManager {

    /**
     * Get a boolean from application preferences
     *
     * @param prefName name of the preference
     * @return value of the preference
     */
    boolean getBoolean(String prefName);

    /**
     * Sets the boolean in the application preferences
     * @param prefName name of the preference
     * @param prefValue value of the preference
     */
    void setBoolean(String prefName, Boolean prefValue);

    /**
     * Sets the string in the application preferences
     * @param prefName name of the preference
     * @param prefValue value of the preference
     */
    void setString(String prefName, String prefValue);

    /**
     * Gets the string in the application preferences
     * @param prefName name of the preference
     */
    String getString(String prefName);
}
