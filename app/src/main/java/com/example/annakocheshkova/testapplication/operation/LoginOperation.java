package com.example.annakocheshkova.testapplication.operation;
import com.example.annakocheshkova.testapplication.response.BaseResponse;
import com.example.annakocheshkova.testapplication.response.LoginResponse;
import com.example.annakocheshkova.testapplication.utils.DateParser;
import com.google.gson.Gson;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Class for sending login http requests
 */
class LoginOperation extends BaseOperation {

    /**
     * Username to be sent (null if there is no)
     */
    private String username;

    /**
     * Password to be sent (null if there is no)
     */
    private String password;

    /**
     * A response sent by server
     */
    private LoginResponse loginResponse;

    /**
     * Creates an instance of LoginOperation
     * @param url to visit
     */
    LoginOperation(String url) {
        super(url);
    }

    /**
     * Creates an instance of LoginOperation by username and password
     * @param url to visit
     * @param username entered by user
     * @param password entered by user
     */
    LoginOperation(String url, String username, String password) {
        super(url);
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

    @Override
    public boolean onFakeResponse() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
        Date date = new Date(calendar.getTimeInMillis());
        String dateString = DateParser.parse(date);
        String json = "{code:200, message:\"\", token:\"fake_token\", userName:\"Anna\", expirationDate:\"" + dateString + "\"}";
        return handleResponse(json);
    }

    /**
     * Gets the instance of Login response
     * @return login response
     */
    LoginResponse getLoginResponse() {
        return loginResponse;
    }
}
