package com.example.annakocheshkova.testapplication.manager.preference;

import android.content.SharedPreferences;
import com.example.annakocheshkova.testapplication.MyApplication;

/**
 * A manager for shared preferences
 */
class SharedPreferencesManager implements PreferencesManager{

    /**
     * instance of SharedPreferences
     */
    private SharedPreferences settings;

    /**
     * Name of the shared preferences
     */
    private static final String appPreferences = "Application preferences";

    /**
     * Creates new instance of shared preferences manager
     */
    SharedPreferencesManager() {
        settings = MyApplication.getAppContext().getSharedPreferences(appPreferences, 0);
    }

    @Override
    public boolean getBoolean(String prefName) {
        return settings.getBoolean(prefName, false);
    }

    @Override
    public void putBoolean(String prefName, Boolean prefValue) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(prefName, prefValue);
        editor.apply();
    }

    @Override
    public void putString(String prefName, String prefValue) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }

    @Override
    public String getString(String prefName) {
        return settings.getString(prefName, "");
    }

    @Override
    public int getInt(String prefName) {
        return settings.getInt(prefName, 0);
    }

    @Override
    public void putInt(String prefName, int prefValue) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(prefName, prefValue);
        editor.apply();
    }
}
