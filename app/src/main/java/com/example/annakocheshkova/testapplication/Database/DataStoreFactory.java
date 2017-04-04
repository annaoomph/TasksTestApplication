package com.example.annakocheshkova.testapplication.Database;

import com.example.annakocheshkova.testapplication.MyApplication;

/**
 * a factory to get datastore object
 */
public class DataStoreFactory {
    public static DataStore getDataStore(){
        return new DatabaseDataStore();
    }
}
