package com.example.annakocheshkova.testapplication;

import android.app.Activity;

/**
 * a factory to get datastore object
 */
public class DataStoreFactory {
    public static DataStore getDataStore(Activity view){
        // TODO Handle with context (and remove this)
        return new DatabaseDataStore(view.getApplicationContext());
    }
}
