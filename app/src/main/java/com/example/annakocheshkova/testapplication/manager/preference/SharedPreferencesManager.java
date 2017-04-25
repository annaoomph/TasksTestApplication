package com.example.annakocheshkova.testapplication.manager.preference;

import android.content.SharedPreferences;
import android.support.v4.util.ArraySet;

import com.example.annakocheshkova.testapplication.MyApplication;

import java.util.Set;

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
    public void setLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(LOGGED_IN, loggedIn);
        editor.apply();
    }

    @Override
    public boolean getLoggedIn() {
        return settings.getBoolean(LOGGED_IN, false);
    }

    @Override
    public void setToken(String token) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    @Override
    public String getToken() {
        return settings.getString(TOKEN, null);
    }

    @Override
    public void addExportDate(String date) {
        Set<String> exportDates = settings.getStringSet(EXPORT_DATES, null);
        if (exportDates == null) {
            exportDates = new ArraySet<>();
        }
        exportDates.add(date);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(EXPORT_DATES, exportDates);
        editor.apply();
    }

    @Override
    public Set<String> getExportDates() {
        return settings.getStringSet(EXPORT_DATES, new ArraySet<String>());
    }

    @Override
    public void setExpirationDate(String date) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(EXPIRE, date);
        editor.apply();
    }

    @Override
    public String getExpirationDate() {
        return settings.getString(EXPIRE, null);
    }
}
