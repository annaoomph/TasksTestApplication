package com.example.annakocheshkova.testapplication.utils.listener;

/**
 * A custom listener of the events connected with export
 */
public interface ExportListener {

    /**
     * Called if user has no privileges to do export
     */
    void onUnauthorized();

    /**
     * Called when data is successfully exported
     */
    void onSuccess();

    /**
     * Called if during the export an IO Error has occurred
     */
    void onIOError();

    /**
     * Called if there is no connection to server
     */
    void onConnectionError();
}
