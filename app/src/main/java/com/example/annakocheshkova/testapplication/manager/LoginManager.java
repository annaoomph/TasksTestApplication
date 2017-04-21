package com.example.annakocheshkova.testapplication.manager;

import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.operation.LoginOperation;
import com.example.annakocheshkova.testapplication.operation.OperationManager;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.LoginListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

/**
 * Manages all the operations connected with login
 */
public class LoginManager {

    /**
     * Tries to login with the given credentials
     * @param username name of the user
     * @param password user's password
     * @param loginListener listener of login events
     */
    public static void login(String username, String password, final LoginListener loginListener) {
        final PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        final LoginOperation loginOperation = new LoginOperation(url, username, password, new OperationListener<LoginOperation>() {

            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                String expirationDate = operation.getExpirationDate();
                preferencesManager.putString(PreferencesManager.EXPIRE, expirationDate);
                preferencesManager.putBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.putString(PreferencesManager.TOKEN, token);
                loginListener.onSuccess();
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                loginListener.onFailure(connectionError);
            }
        });
        OperationManager operationManager = OperationManager.getInstance();
        operationManager.enqueue(loginOperation);
    }

    /**
     * Clears all data about login in preferences
     */
    public static void logout() {
        PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();
        preferencesManager.putBoolean(PreferencesManager.LOGGED_IN, false);
        preferencesManager.putString(PreferencesManager.TOKEN, "");
    }
}
