package com.example.annakocheshkova.testapplication.UI.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.MVC.Controller.ExportController;
import com.example.annakocheshkova.testapplication.MVC.View.ExportView;
import com.example.annakocheshkova.testapplication.R;

import java.io.File;

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
    public String getFolder() {
        return Environment.getExternalStorageDirectory() + "/" + getString(R.string.folder_name) + "/";
    }

    @Override
    public String getNameOrPath() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            return getFolder() + File.separator + fileNameText.getText().toString();
        }
        else return serverText.getText().toString();
    }

    @Override
    public void close() {
            Toast.makeText(this, getString(R.string.file_created) + getNameOrPath(), Toast.LENGTH_LONG).show();
        finish();
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

}
