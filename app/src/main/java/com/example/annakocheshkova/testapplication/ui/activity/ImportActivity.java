package com.example.annakocheshkova.testapplication.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.controller.ImportController;
import com.example.annakocheshkova.testapplication.mvc.view.ImportView;
import com.example.annakocheshkova.testapplication.ui.adapter.FileAdapter;

import java.io.File;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        setContent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
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
        listView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        Button importButton = (Button)findViewById(R.id.import_button);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importController.onImport();
            }
        });
        importController.onViewLoaded();
    }

    @Override
    public void showFiles(List<File> files) {
        fileAdapter.changeData(files);
    }

    @Override
    public void showCorruptFileError() {
        Toast.makeText(this, R.string.corrupt_file_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFileNotChosenError() {
        Toast.makeText(this, R.string.file_not_chosen_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showMessage(int numberOfItems) {
        Toast.makeText(this, numberOfItems + getString(R.string.items_added_label), Toast.LENGTH_LONG).show();
    }

    @Override
    public String getChosenFilePath() {
        return fileAdapter.getChosenItemPath();
    }

}
