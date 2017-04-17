package com.example.annakocheshkova.testapplication.mvc.view;

import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

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
     * Shows the error if there's no connection with server
     */
    void showNoConnectionError();

    /**
     * Shows the error if the path to file is incorrect
     */
    void showWrongFilePathError();

    /**
     * Shows the error if IO error has occured
     */
    void showIOError();

    /**
     * Shows the exception when some feature was not implemented
     * @param exception instance of exception
     */
    void showNotImplementedError(NotImplementedException exception);

    /**
     * Shows the message on export success
     * @param message path details
     */
    void showSuccessMessage(String message);
}
