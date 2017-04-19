package com.example.annakocheshkova.testapplication.operation;

import android.util.ArrayMap;

import com.example.annakocheshkova.testapplication.utils.converter.Converter;
import java.util.Map;

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
    public LoginOperation(String url, Converter<String> converter) {
        super(url, converter);
    }

    /**
     * Creates an instance of LoginOperation by username and password
     * @param url to visit
     * @param username entered by user
     * @param password entered by user
     */
    public LoginOperation(String url, String username, String password, Converter<String> converter) {
        super(url, converter);
        this.username = username;
        this.password = password;
    }

    @Override
    String prepareContent() {
        //TODO Search for a ways to do this
        Map<String, String> credentials = new ArrayMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        return converter.convert(credentials);
    }

    /**
     * Gets the token from response body
     * @return token string
     */
    public String getToken() {
        return this.getResponse();
    }
}
