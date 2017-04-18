package com.example.annakocheshkova.testapplication.utils.preference;

/**
 * A factory that gets preferences manager
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
