package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.MyApplication;

/**
 * a factory to onViewLoaded datastore object
 */
public class DataStoreFactory {
    public static DataStore getDataStore(){
        return new DatabaseDataStore();
    }
}
