package com.example.annakocheshkova.testapplication.ui.activity;

import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.controller.ExportController;
import com.example.annakocheshkova.testapplication.mvc.controller.LoginController;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView{

    CheckBox rememberMe;

    /**
     * A controller for the view
     */
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        loginController = new LoginController(this);
        rememberMe = (CheckBox)findViewById(R.id.remember_me);
        loginController.onViewLoaded();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.login_label);
        }
    }

    @Override
    public String getUsername() {
        EditText usernameText = (EditText)findViewById(R.id.username_text);
        return usernameText.getText().toString();
    }

    @Override
    public String getPassword() {
        EditText passwordText = (EditText)findViewById(R.id.password_text);
        return passwordText.getText().toString();
    }

    @Override
    public void setRememberMe(Boolean remember) {
        rememberMe.setChecked(remember);
    }

    @Override
    public void showNoConnectionError() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWrongCredentialsError() {
        Toast.makeText(this, R.string.wrong_creds_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public boolean ifRememberMe() {
        return rememberMe.isChecked();
    }

}
