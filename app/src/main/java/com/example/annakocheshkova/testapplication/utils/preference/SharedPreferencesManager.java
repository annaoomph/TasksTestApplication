package com.example.annakocheshkova.testapplication.utils.preference;

import android.content.SharedPreferences;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

/**
 * A manager for shared preferences
 */
class SharedPreferencesManager implements PreferencesManager{

    private SharedPreferences settings;

    SharedPreferencesManager() {
        settings = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getString(R.string.app_prefs_name), 0);
    }

    @Override
    public boolean getBoolean(String prefName) {
        return settings.getBoolean(prefName, false);
    }

    @Override
    public void setBoolean(String prefName, Boolean prefValue) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(prefName, prefValue);
        editor.commit();
    }

    @Override
    public void setString(String prefName, String prefValue) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(prefName, prefValue);
        editor.commit();
    }
}
