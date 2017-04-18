package com.example.annakocheshkova.testapplication.mvc.view;
import com.example.annakocheshkova.testapplication.utils.error.BaseError;

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
     * Shows an error
     * @param error to be shown
     */
    void showError(BaseError error);

    /**
     * Closes the view
     */
    void close();
}
