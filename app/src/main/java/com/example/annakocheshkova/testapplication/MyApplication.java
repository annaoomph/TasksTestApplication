package com.example.annakocheshkova.testapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

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

    /**
     * Shows the toast on UI thread
     * @param text text to be shown
     */
    public static void makeToast(final String text) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}