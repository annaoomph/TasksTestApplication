package com.example.annakocheshkova.testapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.annakocheshkova.testapplication.MyApplication;

/**
 * A class that works with network
 */
public class NetworkUtil {

    /**
     * Finds out if the network is available
     * @return true if yes, false otherwise
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
