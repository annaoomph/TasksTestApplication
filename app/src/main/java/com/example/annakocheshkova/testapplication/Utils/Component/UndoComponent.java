package com.example.annakocheshkova.testapplication.Utils.Component;

import com.example.annakocheshkova.testapplication.Utils.Listener.UndoListener;

/**
 * this class handles all undo actions, responsible for storing objects
 * @param <T> Type of the object this component works with
 */
public class UndoComponent<T> {

    /**
     * listener of undo action
     */
    private UndoListener<T> undoListener;

    /**
     * item we are storing right now
     */
    private T item;

    /**
     * constructor of undoComponent, creates an instance of the component with one particular item
     * @param undoListener listener (can be controller)
     */
    public UndoComponent(T item, UndoListener<T> undoListener) {
        this.item = item;
        this.undoListener = undoListener;
    }

    /**
     * restore object
     */
    public void onUndoPressed(){
        undoListener.onUndo(item);
    }

    /**
     * called when storing the object is no longer needed
     */
    public void onHide() {
        this.item = null;
    }
}
