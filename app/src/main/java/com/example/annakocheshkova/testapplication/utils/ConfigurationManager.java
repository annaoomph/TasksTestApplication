package com.example.annakocheshkova.testapplication.utils;

import android.content.res.Resources;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A class that gets configuration data from properties
 */
public class ConfigurationManager {

    public static String getConfigValue(String name) throws IOException {
        Resources resources = MyApplication.getAppContext().getResources();
        InputStream rawResource = resources.openRawResource(R.raw.config);
        Properties properties = new Properties();
        properties.load(rawResource);
        return properties.getProperty(name);
    }
}
