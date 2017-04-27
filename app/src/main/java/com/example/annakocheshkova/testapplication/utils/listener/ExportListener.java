package com.example.annakocheshkova.testapplication.utils.listener;

/**
 * A custom listener of the events connected with export
 */
public interface ExportListener {

    /**
     * Called when data is successfully exported
     */
    void onSuccess();

    /**
     * Called if during the export an IO Error has occurred
     * @param exception error with information about what's happened
     */
    void onError(Exception exception);
}
