package com.example.annakocheshkova.testapplication.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.annakocheshkova.testapplication.R;

/**
 * a view of the fragment, which allows user to add new subtask
 */
public class AlertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText input = new EditText(getActivity());
        DetailedTaskActivity act = (DetailedTaskActivity) getActivity();
        boolean edit = false;
        if (act.getSelectedItem() != null)
        {
            edit = true;
            input.setText(act.getSelectedItem().getName());
        }
        return new AlertDialog.Builder(getActivity())
                .setTitle((edit)?getString(R.string.edit):getString(R.string.enter_new))
                .setView(input)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                ((DetailedTaskActivity) getActivity()).itemCreatedCallback(input.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }).create();
    }
}
