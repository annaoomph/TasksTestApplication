package com.example.annakocheshkova.testapplication.manager.configuration;

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

    /**
     * Name of the property fake request in the config
     */
    public static final String FAKE_REQUEST = "fake_request";

    /**
     * Name of the property server url in the config
     */
    public static final String SERVER_URL = "server_url";

    /**
     * Gets configuration value
     * @param name name of the property
     * @return value of the property
     */
    public static String getConfigValue(String name) {
        try {
            Resources resources = MyApplication.getAppContext().getResources();
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (IOException exception) {
            return null;
        }
    }

    public static Boolean getFakeRequest() {
        String fakeRequestString = getConfigValue(ConfigurationManager.FAKE_REQUEST);
        return fakeRequestString != null && fakeRequestString.equalsIgnoreCase("true");
    }
}
