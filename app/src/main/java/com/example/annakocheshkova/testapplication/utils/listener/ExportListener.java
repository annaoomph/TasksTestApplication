package com.example.annakocheshkova.testapplication.utils.listener;

import com.example.annakocheshkova.testapplication.utils.error.BaseError;

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
     * @param error error with information about what's happened
     */
    void onError(BaseError error);
}
