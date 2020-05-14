package com.example.chatsdkimpldemo.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatsdkimpldemo.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.createDefaultChatSocket(null);
        viewModel.connectWebSocket(null);
    }

    @Override
    protected void onDestroy() {
        viewModel.disconnectWebSocket();
        super.onDestroy();
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }

}
