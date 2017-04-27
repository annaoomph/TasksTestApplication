package com.example.annakocheshkova.testapplication.manager;

import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.operation.LoginOperation;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.operation.OperationRetryComponent;
import com.example.annakocheshkova.testapplication.response.LoginResponse;
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
        final LoginOperation loginOperation = new LoginOperation(url, username, password);
        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(loginOperation, new OperationListener<LoginResponse>() {

            @Override
            public void onSuccess(LoginResponse response) {
                saveLoginData(response);
                loginListener.onSuccess();
            }

            @Override
            public void onFailure(Exception exception) {
                loginListener.onFailure(exception);
            }
        });
    }

    /**
     * Clears all data about login in preferences
     */
    public void logout() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        preferencesManager.setLoggedIn(false);
        preferencesManager.setExpirationDate(0);
        preferencesManager.setToken(null);
    }

    /**
     * Saves the data if the user has just logged in
     * @param response response with all the available information
     */
    private void saveLoginData(LoginResponse response) {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        preferencesManager.setExpirationDate(response.getExpirationDate().getTime());
        preferencesManager.setToken(response.getToken());
        preferencesManager.setLoggedIn(true);
        preferencesManager.setUserId(response.getUserId());
    }

    /**
     * Checks if the token has already expired
     * @return true if we need to relogin, false if not
     */
    private boolean needRelogin() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        long expirationDateMs = preferencesManager.getExpirationDate();
        Date expirationDate = new Date(expirationDateMs);
        Date currentDate = new Date();
        return (expirationDate.compareTo(currentDate) < 0);
    }


    /** Performs relogin if necessary
     * @param operationListener listener that should be notified if relogin failed
     * @return false if relogin has failed and therefore the following operation should not be executed;
     * true if relogin has been successful or not needed at all, says it's ok for the operation to execute.
     */
    public boolean tryRelogin(OperationListener operationListener) {
        return !needRelogin() || reLogin(operationListener);
    }

    /**
     * Tries to relogin with the previous token and get the new one
     * @param operationListener listener that should be notified if relogin has failed
     * @return true if relogin has executed successfully, false otherwise
     */
    private boolean reLogin(OperationListener operationListener) {
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        LoginOperation loginOperation = new LoginOperation(url);
        OperationRetryComponent operationRetryComponent = new OperationRetryComponent();
        if (operationRetryComponent.execute(loginOperation)) {
            saveLoginData(loginOperation.getLoginResponse());
            return true;
        } else {
            logout();
            if (operationListener != null) {
                operationListener.onFailure(loginOperation.getException());
            }
            return false;
        }
    }
}
