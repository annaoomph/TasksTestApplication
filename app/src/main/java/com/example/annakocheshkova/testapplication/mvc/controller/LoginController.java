package com.example.annakocheshkova.testapplication.mvc.controller;

import android.content.Context;
import android.util.ArrayMap;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.manager.converter.Converter;
import com.example.annakocheshkova.testapplication.manager.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.utils.ConfigurationManager;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;
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
        HttpClient httpClient = new HttpClient(this);
        try {
            String url = ConfigurationManager.getConfigValue(MyApplication.getAppContext().getString(R.string.server_url_config_name));
            String fakeRequestString =  ConfigurationManager.getConfigValue(MyApplication.getAppContext().getString(R.string.fake_request_config_name));
            boolean fakeRequest = fakeRequestString.equalsIgnoreCase("true");
            if (fakeRequest) {
                httpClient.doFakeRequest(url);
            } else {
                Map<String, String> credentials = new ArrayMap<>();
                credentials.put("username", loginView.getUsername());
                credentials.put("password", loginView.getPassword());
                Converter<String> converter = ConverterFactory.getConverter();
                String json = converter.convert(credentials);
                httpClient.doPostRequest(url, json);
            }
        } catch (IOException e) {
            loginView.showPropertiesNotFoundError();
        }
    }

    @Override
    public void onSuccess(String response) {
        Context context = MyApplication.getAppContext();
        preferencesManager.setBoolean(context.getString(R.string.loggedIn_pref_name), true);
        preferencesManager.setString(context.getString(R.string.token_pref_name), response);
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
