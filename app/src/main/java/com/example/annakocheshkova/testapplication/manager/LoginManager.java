package com.example.annakocheshkova.testapplication.manager;

import com.example.annakocheshkova.testapplication.error.BaseError;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.operation.LoginOperation;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.utils.DateParser;
import com.example.annakocheshkova.testapplication.utils.listener.LoginListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.Date;

/**
 * Manages all the operations connected with login
 */
public class LoginManager {

    /**
     * A login manager one and only instance
     */
    private static final LoginManager loginManager = new LoginManager();

    /**
     * Gets the only instance of operation manager
     * @return Operation Manager
     */
    public static LoginManager getInstance() {
        return loginManager;
    }

    /**
     * Tries to login with the given credentials
     * @param username name of the user
     * @param password user's password
     * @param loginListener listener of login events
     */
    public void login(String username, String password, final LoginListener loginListener) {
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        final LoginOperation loginOperation = new LoginOperation(url, username, password, new OperationListener<LoginOperation>() {

            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                String expirationDate = operation.getExpirationDate();
                saveLoginData(token, expirationDate);
                loginListener.onSuccess();
            }

            @Override
            public void onFailure(BaseError baseError) {
                loginListener.onFailure(baseError);
            }
        });
        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(loginOperation);
    }

    /**
     * Clears all data about login in preferences
     */
    public void logout() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        preferencesManager.setLoggedIn(false);
        preferencesManager.setExpirationDate(null);
        preferencesManager.setToken(null);
    }

    /**
     * Saves the data if the user has just logged in
     * @param token user token
     * @param expirationDate token expiration date
     */
    private void saveLoginData(String token, String expirationDate) {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        preferencesManager.setExpirationDate(expirationDate);
        preferencesManager.setToken(token);
        preferencesManager.setLoggedIn(true);
    }

    /**
     * Checks if the token has expired aready
     * @return true if we need to relogin, false if not
     */
    public boolean needRelogin() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        String expirationDateString = preferencesManager.getExpirationDate();
        Date expirationDate = DateParser.getInstance().parse(expirationDateString);
        Date currentDate = new Date();
        return (expirationDate == null || expirationDate.compareTo(currentDate) < 0);
    }

    /**
     * Tries to relogin with the previous token
     */
    public void reLogin() {
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        OperationManager operationManager = OperationManager.getInstance();
        LoginOperation loginOperation = new LoginOperation(url, new OperationListener<LoginOperation>() {
            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                String expirationDate = operation.getExpirationDate();
                saveLoginData(token, expirationDate);
            }

            @Override
            public void onFailure(BaseError baseError) {
                logout();
            }
        });
        operationManager.enqueue(loginOperation);
    }
}
