package com.example.annakocheshkova.testapplication.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.manager.configuration.ConfigurationManager;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesFactory;
import com.example.annakocheshkova.testapplication.manager.preference.PreferencesManager;
import com.example.annakocheshkova.testapplication.operation.LoginOperation;
import com.example.annakocheshkova.testapplication.receiver.ReminderAlarmManager;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.converter.ConverterFactory;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.LoginListener;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

/**
 * Manages all the operation connected with login
 */
public class LoginManager extends BroadcastReceiver {

    /**
     * Current preferences manager
     */
    private final static PreferencesManager preferencesManager = PreferencesFactory.getPreferencesManager();

    /**
     * Tries to login with the given credentials
     * @param username name of the user
     * @param password user's password
     * @param loginListener listener of login events
     */
    public static void login(String username, String password, final LoginListener loginListener) {

        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        Converter<String> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
        final LoginOperation loginOperation = new LoginOperation(url, username, password, converter, new OperationListener<LoginOperation>() {

            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                long expirationDate = operation.getExpirationDate();
                Intent alarmIntent = new Intent(MyApplication.getAppContext(), LoginManager.class);
                PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,  expirationDate, alarmPendingIntent);
                preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.setString(PreferencesManager.TOKEN, token);
                loginListener.onSuccess();
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                loginListener.onFailure(connectionError);
            }
        });
        loginOperation.execute();
    }

    /**
     * Gets the new token when the previous has expired
     */
    private static void reLogin() {
        String url = ConfigurationManager.getConfigValue(ConfigurationManager.SERVER_URL);
        Converter<String> converter = ConverterFactory.getConverter(ConverterFactory.ConvertType.JSON);
        LoginOperation loginOperation = new LoginOperation(url, converter, new OperationListener<LoginOperation>() {
            @Override
            public void onSuccess(LoginOperation operation) {
                String token = operation.getToken();
                preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, true);
                preferencesManager.setString(PreferencesManager.TOKEN, token);
            }

            @Override
            public void onFailure(ConnectionError connectionError) {
                logout();
            }
        });
        loginOperation.execute();
    }

    /**
     * Clears all data about login in preferences
     */
    public static void logout() {
        preferencesManager.setBoolean(PreferencesManager.LOGGED_IN, false);
        preferencesManager.setString(PreferencesManager.TOKEN, "");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        reLogin();
    }
}
