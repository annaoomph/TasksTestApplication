package com.example.annakocheshkova.testapplication.UI.Activity;

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
        Bundle bundle = getArguments();
        int id = -1;
        String name = "";
        if (bundle != null) {
            id = bundle.getInt("id", -1);
            name = bundle.getString("name", "");
        }
        final EditText input = new EditText(getActivity());
        boolean edit = false;

        if (id >= 0) {
            edit = true;
            input.setText(name);
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle((edit)?getString(R.string.edit):getString(R.string.enter_new))
                .setView(input)
                .setPositiveButton(getString(R.string.ok_string),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //TODO Do something here
                                ((DetailedTaskActivity) getActivity()).itemCreatedCallback(input.getText().toString());
                            }
                        })
                .setNegativeButton(getString(R.string.cancel_btn),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }).create();
    }
}
