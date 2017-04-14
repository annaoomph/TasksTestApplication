package com.example.annakocheshkova.testapplication.utils.Listener;

/**
 * Listener handling on undo moments in application (when application needs something to be stored permanently and restored in case user clicked Cancel)
 * @param <T> Type of the object we are storing
 */
public interface UndoListener<T> {

    /**
     * Called when user clicked undo
     * @param item item to restore
     */
    void onUndo(T item);
}
