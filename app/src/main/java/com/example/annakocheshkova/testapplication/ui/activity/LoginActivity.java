package com.example.annakocheshkova.testapplication.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.controller.LoginController;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;
import com.example.annakocheshkova.testapplication.utils.error.BaseError;

public class LoginActivity extends BaseActivity implements LoginView{

    /**
     * A controller for the view
     */
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent();
    }

    @Override
    int getLayoutResId() {
        return R.layout.activity_login;
    }

    /**
     * Sets all the content configuration and listeners
     */
    private void setContent() {
        loginController = new LoginController(this);
        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginController.onLoginClicked();
            }
        });
    }

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.login_label);
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
        showToast(error.getErrorMessage());
    }

    @Override
    public void close() {
        finish();
    }
}
