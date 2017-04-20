package com.example.annakocheshkova.testapplication.utils.listener;
import com.example.annakocheshkova.testapplication.error.BaseError;

/**
 * A custom listener of the events connected with import
 */
public interface ImportListener<T> {

    /**
     * Called when data is successfully imported
     * @param data imported data
     */
    void onSuccess(T[] data);

    /**
     * Called if during the export an IO Error has occurred
     * @param error error with information about what's happened
     */
    void onError(BaseError error);
}
