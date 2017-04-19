package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import com.example.annakocheshkova.testapplication.utils.error.ConnectionError;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;

import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Class for handling login http operations
 */
public class LoginOperation extends BaseOperation<String> {

    /**
     * Username to be sent
     */
    private String username;

    /**
     * Password to be sent
     */
    private String password;

    /**
     * Creates an instance of BaseOperation
     * @param url  url to make requests to
     */
    public LoginOperation(String url, Converter<String> converter, OperationListener operationListener) {
        super(url, converter, operationListener);
    }

    /**
     * Creates an instance of LoginOperation by username and password
     * @param url to visit
     * @param username entered by user
     * @param password entered by user
     */
    public LoginOperation(String url, String username, String password, Converter<String> converter, OperationListener operationListener) {
        super(url, converter, operationListener);
        this.username = username;
        this.password = password;
    }

    @Override
    RequestBody prepareContent() {
        if (username == null || password == null) {
            return null;
        } else {
            return new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();
        }
    }

    /**
     * Gets the token from response body
     * @return token string
     */
    public String getToken() {
        return responseObject.getToken();
    }

    /**
     * Gets the expiration date from response body
     * @return expiration date in milliseconds
     */
    public long getExpirationDate() {
        return responseObject.getExpirationDate();
    }

    @Override
    public void onFakeResponse() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
        responseObject = new BaseResponse("token", calendar.getTimeInMillis(), null);
        if (username != null && password != null) {
            if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                operationListener.onSuccess(this);
            } else {
                operationListener.onFailure(new ConnectionError(401));
            }
        } else {
            operationListener.onSuccess(this);
        }
    }
}
