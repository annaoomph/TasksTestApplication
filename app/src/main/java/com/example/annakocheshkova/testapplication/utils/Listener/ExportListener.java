package com.example.annakocheshkova.testapplication.utils.Listener;

/**
 * A custom listener of the events connected with export
 */
public interface ExportListener {

    /**
     * Called if user has no privileges to do export
     */
    void onUnauthorized();

    /**
     * Called when data was successfully exported
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
