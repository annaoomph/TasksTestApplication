package com.example.annakocheshkova.testapplication.mvc.view;

/**
 * An interface for the Export Activity
 */
public interface ExportView {

    /**
     * Returns if the export should be local
     * @return true if local, false if remote
     */
    boolean isLocal();

    /**
     * Gets the name of the file or the path to the server depending on the chosen export type
     * @return name of the file / path to server
     */
    String getNameOrPath();

    /**
     * Closes the view after success
     */
    void close();

    /**
     * Shows the error
     * @param exception to be shown
     */
    void showError(Exception exception);

    /**
     * Shows or hides the content that is available only for logged in users
     * @param loggedIn true if user is logged in
     */
    void setLoggedIn(boolean loggedIn);

    /**
     * Shows the message on export success
     * @param path path where the application has exported the files to
     */
    void showSuccessMessage(String path);

}
