package com.example.annakocheshkova.testapplication.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.mvc.controller.ExportController;
import com.example.annakocheshkova.testapplication.mvc.view.ExportView;
import com.example.annakocheshkova.testapplication.R;

public class ExportActivity extends AppCompatActivity implements ExportView {

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
     * sets all the content configuration and listeners
     */
    private void setContent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exportController = new ExportController(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.export_title);
        }
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
                openLogin();
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

    private void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean isLocal() {
        return radioGroup.getCheckedRadioButtonId() == R.id.local_button;
    }

    @Override
    public String getNameOrPath() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            return fileNameText.getText().toString();
        }
        else return serverText.getText().toString();
    }

    @Override
    public void close() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            Toast.makeText(this, getString(R.string.file_created) + getNameOrPath(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.server_success) + getNameOrPath(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void showNoConnectionError() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIOError() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            Toast.makeText(this, R.string.io_error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.io_server_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showExtraContent(boolean loggedIn) {
        if (loggedIn) {
            serverButton.setEnabled(true);
            loginLink.setVisibility(View.GONE);
        } else {
            serverButton.setEnabled(false);
            loginLink.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showUnauthorizedError() {
        Toast.makeText(this, R.string.unauthorized_export_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
