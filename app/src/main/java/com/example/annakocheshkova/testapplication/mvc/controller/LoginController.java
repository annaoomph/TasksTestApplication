package com.example.annakocheshkova.testapplication.mvc.controller;

import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.utils.Listener.HttpListener;

import java.io.IOException;

/**
 * A controller that handles login
 */
public class LoginController implements HttpListener{

    /**
     * A view for the controller
     */
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * Called when user clicked login
     */
    public void onLoginClicked() {}

    @Override
    public void onFailure() throws IOException {

    }

    @Override
    public void onSuccess(String response) {

    }
}
