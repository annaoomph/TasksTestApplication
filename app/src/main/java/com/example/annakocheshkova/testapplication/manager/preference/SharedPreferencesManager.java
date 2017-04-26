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
        return settings.getString(TOKEN, "");
    }

    @Override
    public void setUserId(int id) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(USER_ID, id);
        editor.apply();
    }

    @Override
    public int getUserId() {
        return settings.getInt(USER_ID, 0);
    }

    @Override
    public void setExpirationDate(long date) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(EXPIRE, date);
        editor.apply();
    }

    @Override
    public long getExpirationDate() {
        return settings.getLong(EXPIRE, 0);
    }

    @Override
    public int getMaxTry() {
        return settings.getInt(MAX_TRY, 3);
    }

    @Override
    public void setMaxTry(int maxTry) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MAX_TRY, maxTry);
        editor.apply();
    }

    @Override
    public long getRetryWait() {
        return settings.getLong(RETRY_WAIT, 5000);
    }

    @Override
    public void setRetryWait(long retryWait) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(RETRY_WAIT, retryWait);
        editor.apply();
    }
}
