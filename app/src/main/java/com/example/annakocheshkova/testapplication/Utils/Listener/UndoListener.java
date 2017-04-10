package com.example.annakocheshkova.testapplication.Utils.Listener;

/**
 * A listener handling on undo moments in application (when application needs something to be stored permanently and restored in case user clicked Cancel)
 * @param <T> Type of the object we are storing
 */
public interface UndoListener<T> {

    /**
     * Called when user clicked cancel or has chosen to undo his actions any other way
     * @param item item to restore
     */
    void onUndo(T item);
}
