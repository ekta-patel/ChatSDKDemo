package com.example.chatsdkimpldemo;

import android.app.Application;

import com.example.mychatlibrary.data.local.AppSharedPrefManager;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {

    private static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(MyApp.this);
        AppSharedPrefManager.initInstance(MyApp.this);
    }

    public static MyApp getInstance() {
        return instance;
    }
}
