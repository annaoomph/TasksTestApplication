package com.example.annakocheshkova.testapplication.Utils.Listener;

/**
 * listener handling on undo moments in application (when application needs something to be stored permanently and restored in case user clicked Cancel)
 * @param <T> Type of the object we are storing
 */
public interface UndoListener<T> {

    /**
     * called when item has been deleted;
     * deletes item
     * @param position of the item to be deleted
     */
    T onDelete(int position);

    /**
     * called when user clicked cancel or has chosen to undo his actions any other way
     * restores created item
     * @param item item to restore
     */
    void onUndo(T item);
}
