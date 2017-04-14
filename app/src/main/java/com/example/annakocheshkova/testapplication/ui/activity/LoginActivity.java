package com.example.annakocheshkova.testapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.annakocheshkova.testapplication.R;
import com.example.annakocheshkova.testapplication.mvc.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
