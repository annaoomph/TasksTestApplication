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
     * Sets checkbox remember me
     * @param remember to be shown
     */
    void setRememberMe(Boolean remember);

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
     * Returns if user has chosen to remember his credentials
     * @return true if yes
     */
    boolean ifRememberMe();
}
