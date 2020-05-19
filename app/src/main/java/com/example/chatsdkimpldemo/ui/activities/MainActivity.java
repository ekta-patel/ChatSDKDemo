package com.example.chatsdkimpldemo.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ActivityMainBinding;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.utils.Utilities;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.createDefaultChatSocket();
        if (Utilities.hasNetwork(MainActivity.this)) {
            viewModel.connectWebSocket(null);
        } else {
            Snackbar.make(binding.getRoot(), "Internet may not be available", Snackbar.LENGTH_LONG).show();
        }

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/") || type.startsWith("video/") || type.startsWith("audio/") || type.startsWith("application/")) {
                handleIncomingData(intent);
            } else if (type.startsWith("text/")) {
                handleIncomingTextData(intent);
            }
        }
    }

    private void handleIncomingTextData(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BundleKeys.SHARED_TEXT, sharedText);
            bundle.putBoolean(Constants.BundleKeys.IS_TEXT_SHARED, true);
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_selectUsersForShareFragment, bundle);
        }
    }

    void handleIncomingData(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BundleKeys.SHARED_FILE_URI, imageUri);
            bundle.putBoolean(Constants.BundleKeys.IS_TEXT_SHARED, false);
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_global_selectUsersForShareFragment, bundle);
        }
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
