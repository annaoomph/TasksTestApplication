package com.example.annakocheshkova.testapplication.MVC.Controller;

import com.example.annakocheshkova.testapplication.Database.DataStore;
import com.example.annakocheshkova.testapplication.Database.DataStoreFactory;
import com.example.annakocheshkova.testapplication.MVC.View.ExportView;

/**
 * A controller to handle export view
 */
public class ExportController {

    /**
     * this controller's main view
     */
    private ExportView mainView;

    /**
     * dataStore to work with data
     */
    private DataStore dataStore;

    /**
     * creates new instance of the xport controller
     * @param view main view
     */
    public ExportController(ExportView view) {
        this.mainView = view;
        dataStore = DataStoreFactory.getDataStore();
    }
}
