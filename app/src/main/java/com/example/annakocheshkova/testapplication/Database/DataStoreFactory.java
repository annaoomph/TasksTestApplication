package com.example.annakocheshkova.testapplication.Database;

/**
 * a factory to get datastore object
 */
public class DataStoreFactory {

    /**
     * gets an example of datastore object
     * @return datastore object
     */
    public static DataStore getDataStore(){
        return new DatabaseDataStore();
    }
}
