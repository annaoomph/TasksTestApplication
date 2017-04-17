package com.example.annakocheshkova.testapplication.utils.preference;

/**
 * Factory gets current preferences manager
 */
public class PreferencesFactory {

    /**
     * Gets preferences manager
     * @return preferences manager
     */
    public static PreferencesManager getPreferencesManager() {
        return new SharedPreferencesManager();
    }
}
