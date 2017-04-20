package com.example.annakocheshkova.testapplication.operation;

import com.example.annakocheshkova.testapplication.manager.LoginManager;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.Calendar;

/**
 * A class that manages all operations
 */
public class OperationManager {

    /**
     * Operation to be executed
     */
    private BaseOperation baseOperation;

    /**
     * Application preferences manager
     */
    private PreferencesManager preferencesManager;

    /**
     * Creates new instance of the manager
     * @param baseOperation operation to be executed
     */
    public OperationManager(BaseOperation baseOperation) {
        this.baseOperation = baseOperation;
        this.preferencesManager = PreferencesFactory.getPreferencesManager();
    }

    /**
     * Executes the operation and checks if the token is valid
     */
    public void executeOperation() {
        long expirationDate = preferencesManager.getLong(PreferencesManager.EXPIRE);
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        if (expirationDate > currentTime) {
            reLogin();
        }
        baseOperation.execute();
    }

    /**
     * Gets the new token when the previous one has expired
     */
    private void reLogin() {
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        LoginOperation loginOperation = new LoginOperation(url, new OperationListener<LoginOperation>() {
            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                long expirationDate = operation.getExpirationDate();
                preferencesManager.putLong(PreferencesManager.EXPIRE, expirationDate);
                preferencesManager.putBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.putString(PreferencesManager.TOKEN, token);
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                LoginManager.logout();
            }
        });
        loginOperation.execute();
    }
}
