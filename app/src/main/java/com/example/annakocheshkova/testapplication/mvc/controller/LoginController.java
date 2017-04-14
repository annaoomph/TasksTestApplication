package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.utils.HttpClient;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.utils.preference.PreferencesManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A controller that handles login
 */
public class LoginController implements Callback {

    /**
     * Current preferences manager
     */
    private PreferencesManager preferencesManager;

    /**
     * A view for the controller
     */
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        preferencesManager = PreferencesFactory.getPreferencesManager();
    }

    /**
     * Called when user clicked login
     */
    public void onLoginClicked(){
        HttpClient httpClient = new HttpClient(this);
        //TODO get url from config
        //ToDO serialize username and password
        String url = "";
        httpClient.doGetRequest(url);
    }

    /**
     * Called when the view was loaded
     */
    public void onViewLoaded() {
        Boolean remember = preferencesManager.getBoolean("remember");
        loginView.setRememberMe(remember);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        loginView.showNoConnectionError();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            preferencesManager.setBoolean("loggedIn", true);
            preferencesManager.setBoolean("remember", loginView.ifRememberMe());
            preferencesManager.setString("token", response.body().toString());
            loginView.close();
        } else {
            loginView.showWrongCredentialsError();
        }
    }
}
