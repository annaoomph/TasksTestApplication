package com.example.annakocheshkova.testapplication.Utils.Component;

import com.example.annakocheshkova.testapplication.Utils.Listener.UndoListener;

/**
 * this class handles all undo actions, responsible for storing objects
 * @param <T> Type of the object this component works with
 */
public class UndoComponent<T> {

    /**
     * listener of undo and delete actions
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
     * cancel deleting object
     */
    public void Cancel(){
        undoListener.onUndo(item);
    }


}
