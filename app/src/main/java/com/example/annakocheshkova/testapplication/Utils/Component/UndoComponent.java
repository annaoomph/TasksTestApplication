package com.example.annakocheshkova.testapplication.Utils.Component;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
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
     */
    public UndoComponent() {
    }

    public void make(View view, T item, UndoListener<T> undoListener, String name) {
        this.item = item;
        this.undoListener = undoListener;
        Snackbar.make(view, MyApplication.getAppContext().getString(R.string.deleted_string_firstpart)+" " + name+" "+ MyApplication.getAppContext().getString(R.string.deleted_string_secondpart), Snackbar.LENGTH_LONG)
                .setAction(R.string.cancel_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onUndoPressed();
                    }
                }).show();
    }

    /**
     * restore object
     */
    private void onUndoPressed(){
        undoListener.onUndo(item);
    }

}
