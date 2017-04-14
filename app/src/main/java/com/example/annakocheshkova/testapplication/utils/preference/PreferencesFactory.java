package com.example.annakocheshkova.testapplication.utils.preference;

/**
 * Factory gets current preferences manager
 */
public class PreferencesFactory {

    /**
     * Gets current preferences manager
     * @return manager
     */
    public static PreferencesManager getPreferencesManager() {
        return new SharedPreferencesManager();
    }
}
