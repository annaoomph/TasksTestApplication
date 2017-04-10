package com.example.annakocheshkova.testapplication;

import android.app.Application;
import android.content.Context;

/**
 * A class for getting the context with static method
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}