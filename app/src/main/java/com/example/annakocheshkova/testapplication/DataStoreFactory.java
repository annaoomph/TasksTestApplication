package com.example.annakocheshkova.testapplication;

import android.app.Activity;
import android.view.View;

public class DataStoreFactory {
    public static DataStore getDataStore(Activity view){
        // TODO Handle with context (and remove this)
        return new DatabaseDataStore(view.getApplicationContext());
    }
}
