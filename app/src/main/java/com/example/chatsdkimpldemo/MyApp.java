package com.example.chatsdkimpldemo;

import android.app.Application;

import com.example.mychatlibrary.data.local.AppSharedPrefManager;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSharedPrefManager.initInstance(MyApp.this);
    }
}
