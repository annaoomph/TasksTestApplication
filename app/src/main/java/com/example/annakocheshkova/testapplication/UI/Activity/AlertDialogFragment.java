package com.example.annakocheshkova.testapplication.UI.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.annakocheshkova.testapplication.MVC.Controller.DialogController;
import com.example.annakocheshkova.testapplication.MVC.View.DialogView;
import com.example.annakocheshkova.testapplication.Utils.Listener.OnItemEditedListener;
import com.example.annakocheshkova.testapplication.R;

/**
 * a view of the fragment, which allows user to add new subtask
 */
public class AlertDialogFragment extends DialogFragment implements DialogView {

    /**
     * controller for this view
     */
    DialogController dialogController;

    /**
     * sets listener that responds when item has been edited or created
     * @param onItemEditedListener listener
     */
    public void setOnItemEditedListener(OnItemEditedListener onItemEditedListener) {
        dialogController = new DialogController(this);
        dialogController.setOnItemEditedListener(onItemEditedListener);
    }

    /**
     * main (and only) editText to enter the name of the subtask
     */
    EditText input;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int id = -1;
        int taskId = -1;
        if (bundle != null) {
            id = bundle.getInt("id", -1);
            taskId = bundle.getInt("taskId", -1);
        }
        input = new EditText(getActivity());
        boolean edit = false;
        dialogController.onDialogLoaded(taskId, id);

        if (id >= 0) {
            edit = true;
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle((edit)?getString(R.string.edit_item_title):getString(R.string.enter_new_title))
                .setView(input)
                .setPositiveButton(getString(R.string.ok_string),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialogController.onEditingEnded(input.getText().toString());
                            }
                        })
                .setNegativeButton(getString(R.string.cancel_btn),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }).create();
    }

    /**
     * show the item name if it is edited
     * @param subTaskName name of the editing subtask
     */
    @Override
    public void showEditingItem(String subTaskName) {
        input.setText(subTaskName);
    }
}
