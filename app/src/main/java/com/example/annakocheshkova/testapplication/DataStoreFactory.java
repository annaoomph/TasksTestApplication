package com.example.annakocheshkova.testapplication;

import android.app.Activity;

import com.example.annakocheshkova.testapplication.Views.SubTaskView;

/**
 * a factory to get datastore object
 */
public class DataStoreFactory {
    public static DataStore getDataStore(){
        return new DatabaseDataStore(MyApplication.getAppContext());
    }
}
