package com.example.annakocheshkova.testapplication.mvc.view;

/**
 * An interface for the login activity
 */
public interface LoginView {

    /**
     * Gets entered username
     * @return username
     */
    String getUsername();

    /**
     * Gets entered password
     * @return password
     */
    String getPassword();

    /**
     * Shows an error if there's no connection to server
     */
    void showNoConnectionError();

    /**
     * Shows an error if the credentials were wrong
     */
    void showWrongCredentialsError();

    /**
     * Closes the view
     */
    void close();

    /**
     * Shows an error if the controller can't find properties
     */
    void showPropertiesNotFoundError();
}
