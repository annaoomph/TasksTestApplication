package com.example.annakocheshkova.testapplication.UI.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.example.annakocheshkova.testapplication.MVC.Controller.SubTaskController;
import com.example.annakocheshkova.testapplication.MVC.View.ExportView;
import com.example.annakocheshkova.testapplication.Model.SubTask;
import com.example.annakocheshkova.testapplication.MyApplication;
import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.UI.Adapter.SubTaskAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            actionBar.setTitle(R.string.export);
        }
        fileNameText = (EditText)findViewById(R.id.file_name);
        serverText = (EditText)findViewById(R.id.server_path);
        serverText.setVisibility(View.GONE);
        final LinearLayout openFolderLayout = (LinearLayout)findViewById(R.id.open_folder_layout);
        radioGroup = (RadioGroup)findViewById(R.id.radio_button_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.local_button) {
                    fileNameText.setVisibility(View.VISIBLE);
                    openFolderLayout.setVisibility(View.VISIBLE);
                    serverText.setVisibility(View.GONE);
                } else {
                    serverText.setVisibility(View.VISIBLE);
                    openFolderLayout.setVisibility(View.GONE);
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
        boolean isLogged = false;
        if (!isLogged) {
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
        CheckBox openFolderCheckBox = (CheckBox)findViewById(R.id.open_folder);
        boolean openFolder = openFolderCheckBox.isChecked();
        if (openFolder) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + getString(R.string.folder_name) + "/");
            intent.setDataAndType(uri,"*/*");
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.file_created) + getNameOrPath(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void showNoConnectionError() {
        Toast.makeText(this, R.string.no_connection , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWrongFilePathError() {
        Toast.makeText(this, R.string.wrong_path , Toast.LENGTH_LONG).show();

    }

    @Override
    public void showIOError() {
        Toast.makeText(this, R.string.io_error , Toast.LENGTH_LONG).show();
    }

}
