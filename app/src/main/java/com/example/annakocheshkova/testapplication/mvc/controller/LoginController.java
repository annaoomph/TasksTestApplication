package com.example.annakocheshkova.testapplication.mvc.controller;

import android.util.ArrayMap;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.utils.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.client.BaseHttpClient;
import com.example.annakocheshkova.testapplication.client.BaseHttpClientFactory;
import com.example.annakocheshkova.testapplication.utils.listener.HttpListener;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesManager;

import java.io.IOException;
import java.util.Map;

/**
 * A controller that handles login
 */
public class LoginController implements HttpListener {

    /**
     * Current preferences manager
     */
    private PreferencesManager preferencesManager;

    /**
     * A view for the controller
     */
    private LoginView loginView;

    /**
     * Creates an instance of login controller
     * @param loginView view that this controller handles
     */
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        preferencesManager = PreferencesFactory.getPreferencesManager();
    }

    /**
     * Called when user clicked login
     */
    public void onLoginClicked(){
        try {
            String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
            BaseHttpClient httpClient = BaseHttpClientFactory.getHttpClient(this);
            Map<String, String> credentials = new ArrayMap<>();
            credentials.put("username", loginView.getUsername());
            credentials.put("password", loginView.getPassword());
            Converter<String> converter = ConverterFactory.getConverter();
            String json = converter.convert(credentials);
            httpClient.doPostRequest(url, json);
        } catch (IOException e) {
            loginView.showPropertiesNotFoundError();
        }
    }

    @Override
    public void onSuccess(String response) {
        preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, true);
        preferencesManager.setString(PreferencesManager.TOKEN, response);
        loginView.close();
    }

    @Override
    public void onFailure() {
        loginView.showNoConnectionError();
    }

    @Override
    public void onUnauthorized() {
        loginView.showWrongCredentialsError();
    }
}
