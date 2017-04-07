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
     * creates an instance of the component
     */
    public UndoComponent() {
    }

    /**
     * method saves an item to the component and shows toast notifying user what happened to the item
     * @param view view in which the toast should be shown
     * @param item item to be saved
     * @param undoListener listener of undo event
     * @param name name of the item to be shown in a toast
     */
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
     * event when undo button was pressed
     */
    private void onUndoPressed(){
        undoListener.onUndo(item);
    }

}
