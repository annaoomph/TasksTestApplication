package com.example.annakocheshkova.testapplication.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.controller.LoginController;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.error.BaseError;

public class LoginActivity extends AppCompatActivity implements LoginView{

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
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets all the content configuration and listeners
     */
    private void setContent() {
        loginController = new LoginController(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.login_label);
        }
        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginController.onLoginClicked();
            }
        });
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
    public void showError(BaseError error) {
        Toast.makeText(this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }
}
