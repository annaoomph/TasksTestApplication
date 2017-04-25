package com.example.annakocheshkova.testapplication.mvc.controller;
import android.support.annotation.UiThread;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.manager.LoginManager;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.LoginListener;

/**
 * A controller that handles login
 */
public class LoginController {

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
    }

    /**
     * Called when user clicked login
     */
    public void onLoginClicked(){
        LoginManager.getInstance().login(loginView.getUsername(), loginView.getPassword(), new LoginListener() {
            @Override
            public void onSuccess() {
                loginView.close();
            }

            @Override
            public void onFailure(BaseError baseError) {
                loginView.showError(baseError);
            }
        });
    }
}
