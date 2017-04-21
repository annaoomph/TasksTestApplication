package com.example.annakocheshkova.testapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.controller.ImportController;
import com.example.annakocheshkova.testapplication.mvc.view.ImportView;
import com.example.annakocheshkova.testapplication.ui.adapter.FileAdapter;

import java.io.File;
import java.util.List;

/**
 * An activity for file import
 */
public class ImportActivity extends BaseActivity implements ImportView {

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
        setContent();
    }

    @Override
    int getLayoutResId() {
        return R.layout.activity_import;
    }

    /**
     * Sets all the content configuration and listeners
     */
    private void setContent() {
        RecyclerView listView = (RecyclerView)findViewById(R.id.files_view);
        importController = new ImportController(this);
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
        importController.onViewLoaded();
    }

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.import_label);
    }

    @Override
    public void showFiles(List<File> files) {
        fileAdapter.setData(files);
    }

    @Override
    public void showCorruptFileError() {
        showToast(getString(R.string.corrupt_file_error));
    }

    @Override
    public void showFileNotChosenError() {
        showToast(getString(R.string.file_not_chosen_error));
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showSuccessMessage(int numberOfItems) {
        showToast(numberOfItems + getString(R.string.items_added_label));
    }

    @Override
    public String getChosenFilePath() {
        return fileAdapter.getChosenItemPath();
    }
}
