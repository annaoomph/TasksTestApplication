package com.example.annakocheshkova.testapplication.ui.activity;

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

import com.example.annakocheshkova.testapplication.manager.FileManager;
import com.example.annakocheshkova.testapplication.mvc.controller.ExportController;
import com.example.annakocheshkova.testapplication.mvc.view.ExportView;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.utils.NotImplementedException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setContent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets all the content configuration and listeners
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
        RadioButton serverButton = (RadioButton)findViewById(R.id.remote_button);
        TextView loginLink = (TextView)findViewById(R.id.login_link_view);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
        //TODO set isLogged
        boolean isLoggedIn = false;
        if (!isLoggedIn) {
            serverButton.setEnabled(false);
            loginLink.setVisibility(View.VISIBLE);
        }
        Button exportButton = (Button)findViewById(R.id.button);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportController.onExport();
            }
        });
    }

    private void openLogin() {
        //TODO open login from...
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
        Toast.makeText(this, getString(R.string.file_created) + path, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoConnectionError() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWrongFilePathError() {
        Toast.makeText(this, R.string.wrong_path_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIOError() {
        Toast.makeText(this, R.string.io_error , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNotImplementedError(NotImplementedException exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
