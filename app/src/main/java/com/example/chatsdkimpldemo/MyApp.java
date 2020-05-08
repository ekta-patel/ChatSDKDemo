package com.example.chatsdkimpldemo;

import android.app.Application;

import com.example.mychatlibrary.data.local.AppSharedPrefManager;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(MyApp.this);
        AppSharedPrefManager.initInstance(MyApp.this);
    }
}
