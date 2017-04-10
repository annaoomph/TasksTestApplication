package com.example.annakocheshkova.testapplication.Database;

/**
 * A factory to get datastore object
 */
public class DataStoreFactory {

    /**
     * Gets an example of datastore object
     * @return datastore object
     */
    public static DataStore getDataStore(){
        return new DatabaseDataStore();
    }
}
