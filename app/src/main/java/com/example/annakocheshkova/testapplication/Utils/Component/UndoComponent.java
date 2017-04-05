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
     * position in the array of the item
     */
    private int position;

    /**
     * constructor
     * @param position of the item to be stored
     * @param undoListener listener (can be controller)
     */
    public UndoComponent(int position, UndoListener<T> undoListener) {
        this.position = position;
        this.undoListener = undoListener;
    }

    /**
     * delete object permanently
     */
    public void Delete(){
        item = undoListener.onDelete(position);
    }

    /**
     * cancel deleting object
     */
    public void Cancel(){
        undoListener.onUndo(item);
    }


}
