package com.example.chatsdkimpldemo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.chatsdkimpldemo.R;
import com.example.mychatlibrary.data.local.AppSharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(AppSharedPrefManager.getToken())) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
    }
}
