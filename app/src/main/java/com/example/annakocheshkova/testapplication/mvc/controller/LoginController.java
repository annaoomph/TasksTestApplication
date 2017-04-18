package com.example.annakocheshkova.testapplication.mvc.controller;
import com.example.annakocheshkova.testapplication.operation.LoginOperation;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

/**
 * A controller that handles login
 */
public class LoginController {

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
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        Converter<String> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
        final LoginOperation loginOperation = new LoginOperation(url, loginView.getUsername(), loginView.getPassword(), converter);
        loginOperation.executePost(new OperationListener<LoginOperation>() {

            @Override
            public void onSuccess(LoginOperation operation) {
                //TODO Probably remove operation parameter
                String token = operation.getToken();
                preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.setString(PreferencesManager.TOKEN, token);
                loginView.close();
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                loginView.showError(connectionError);
            }
        });
    }
}
