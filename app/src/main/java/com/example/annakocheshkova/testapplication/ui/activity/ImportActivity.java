package com.example.annakocheshkova.testapplication.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.controller.ImportController;
import com.example.annakocheshkova.testapplication.mvc.view.ImportView;
import com.example.annakocheshkova.testapplication.ui.adapter.FileAdapter;
import com.example.annakocheshkova.testapplication.utils.error.BaseError;
import com.example.annakocheshkova.testapplication.utils.error.FileError;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * An activity for file import
 */
public class ImportActivity extends AppCompatActivity implements ImportView{

    /**
     * Main controller for the view
     */
    ImportController importController;

    /**
     * An adapter for File Recycler view
     */
    FileAdapter fileAdapter;

    /**
     * A group of radio buttons determining whether the export should be local or remote
     */
    RadioGroup radioGroup;

    /**
     * A layout for local import
     */
    LinearLayout localLayout;

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
        setContentView(R.layout.activity_importer);
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

    @Override
    protected void onResume() {
        super.onResume();
        importController.onViewLoaded();
    }

    /**
     * Sets all the content configuration and listeners
     */
    private void setContent() {
        RecyclerView listView = (RecyclerView)findViewById(R.id.files_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        importController = new ImportController(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.import_label));
        }
        fileAdapter = new FileAdapter();
        listView.setAdapter(fileAdapter);
        listView.setHasFixedSize(true); //for better performance
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        Button importButton = (Button)findViewById(R.id.import_button);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importController.onImport();
            }
        });
        localLayout = (LinearLayout)findViewById(R.id.local_import);
        serverText = (EditText)findViewById(R.id.server_path);
        serverText.setVisibility(View.GONE);
        radioGroup = (RadioGroup)findViewById(R.id.radio_button_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.local_button) {
                    localLayout.setVisibility(View.VISIBLE);
                    serverText.setVisibility(View.GONE);
                } else {
                    serverText.setVisibility(View.VISIBLE);
                    localLayout.setVisibility(View.GONE);
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
        importController.onViewLoaded();
    }

    private void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFiles(List<File> files) {
        fileAdapter.setData(files);
    }

    @Override
    public void showError(BaseError error) {
        Toast.makeText(this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFileNotChosenError() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            Toast.makeText(this, R.string.file_not_chosen_error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.url_empty_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showSuccessMessage(int numberOfItems) {
        Toast.makeText(this, numberOfItems + getString(R.string.items_added_label), Toast.LENGTH_LONG).show();
    }

    @Override
    public String getNameOrPath() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.local_button) {
            return fileAdapter.getChosenItemPath();
        } else {
            return serverText.getText().toString();
        }
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

    @Override
    public boolean isLocal() {
        return radioGroup.getCheckedRadioButtonId() == R.id.local_button;
    }
}
