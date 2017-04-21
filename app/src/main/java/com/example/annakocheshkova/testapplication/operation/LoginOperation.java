package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.response.LoginResponse;
import com.example.annakocheshkova.testapplication.utils.listener.OperationListener;
import com.google.gson.Gson;

import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Class for handling login http operations
 */
public class LoginOperation extends BaseOperation {

    /**
     * Username to be sent
     */
    private String username;

    /**
     * Password to be sent
     */
    private String password;

    /**
     * A response sent by server
     */
    private LoginResponse loginResponse;


    /**
     * Creates an instance of LoginOperation by username and password
     * @param url to visit
     */
    LoginOperation(String url, OperationListener operationListener) {
        super(url, operationListener);
    }

    /**
     * Creates an instance of LoginOperation by username and password
     * @param url to visit
     * @param username entered by user
     * @param password entered by user
     */
    public LoginOperation(String url, String username, String password, OperationListener operationListener) {
        super(url, operationListener);
        this.username = username;
        this.password = password;
    }

    @Override
    RequestBody preparePostContent() {
        if (username == null || password == null) {
            return null;
        } else {
            return new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();
        }
    }

    @Override
    String prepareGetContent() {
        return "";
    }

    @Override
    void parseResponse(String responseJson) {
        Gson gson = new Gson();
        loginResponse = gson.fromJson(responseJson, LoginResponse.class);
    }

    @Override
    BaseResponse getBaseResponse() {
        return loginResponse;
    }

    @Override
    RequestType getRequestType() {
        if (username == null || password == null) {
            return RequestType.GET;
        } else {
            return RequestType.POST;
        }
    }

    /**
     * Gets the token from response body
     * @return token string
     */
    public String getToken() {
        return loginResponse.getToken();
    }

    /**
     * Gets the expiration date from response body
     * @return expiration date in string format
     */
    public String getExpirationDate() {
        return loginResponse.getExpirationDate();
    }

    @Override
    public void onFakeResponse() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);

        String fakeJson = "{code:200, message:\"\", token:\"token\", expirationDate:\"4/21/17 3:29:17 PM GMT+00:00\"}";
        handleResponse(fakeJson);
    }
}
