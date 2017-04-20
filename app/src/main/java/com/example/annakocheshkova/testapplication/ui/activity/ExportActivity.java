package com.example.annakocheshkova.testapplication.ui.activity;

import android.support.annotation.IdRes;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.annakocheshkova.testapplication.mvc.controller.ExportController;
import com.example.annakocheshkova.testapplication.mvc.view.ExportView;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.error.BaseError;

public class ExportActivity extends BaseActivity implements ExportView {

    /**
     * Main view controller
     */
    ExportController exportController;

    /**
     * A group of radio buttons determining whether the export should be local or remote
     */
    RadioGroup radioGroup;

    /**
     * An input for the file name
     */
    EditText fileNameText;

    /**
     * An input for the path to server
     */
    EditText serverText;

    /**
     * RadioButton for choosing server export
     */
    RadioButton serverButton;

    /**
     * A link redirecting user to login screen
     */
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        exportController.onViewLoaded();
    }

    /**
     * Sets all the content configuration and listeners
     */
    private void setContent() {
        exportController = new ExportController(this);
        fileNameText = (EditText)findViewById(R.id.file_name);
        serverText = (EditText)findViewById(R.id.server_path);
        serverText.setVisibility(View.GONE);
        radioGroup = (RadioGroup)findViewById(R.id.radio_button_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.local_button) {
                    fileNameText.setVisibility(View.VISIBLE);
                    serverText.setVisibility(View.GONE);
                } else {
                    serverText.setVisibility(View.VISIBLE);
                    fileNameText.setVisibility(View.GONE);
                }
            }
        });
        serverButton = (RadioButton)findViewById(R.id.remote_button);
        loginLink = (TextView)findViewById(R.id.login_link_view);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginScreen();
            }
        });
        exportController.onViewLoaded();
        Button exportButton = (Button)findViewById(R.id.button);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportController.onExport();
            }
        });
    }

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.export_title);
    }

    @Override
    public boolean isLocal() {
        return radioGroup.getCheckedRadioButtonId() == R.id.local_button;
    }

    @Override
    public String getNameOrPath() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            return fileNameText.getText().toString();
        } else {
            return serverText.getText().toString();
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showSuccessMessage(String path) {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            showToast(getString(R.string.file_created) + getNameOrPath());
        } else {
            showToast(getString(R.string.server_success) + getNameOrPath());
        }
    }

    @Override
    public void showError(BaseError error) {
        showToast(error.getErrorMessage());
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        if (loggedIn) {
            serverButton.setEnabled(true);
            loginLink.setVisibility(View.GONE);
        } else {
            serverButton.setEnabled(false);
            loginLink.setVisibility(View.VISIBLE);
        }
    }
}
