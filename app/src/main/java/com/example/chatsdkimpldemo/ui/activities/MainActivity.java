package com.example.chatsdkimpldemo.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.chatsdkimpldemo.R;
import com.example.mychatlibrary.ConfigChatSocket;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel viewModel;
    private ConfigChatSocket chatSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setUpToolbarWithNavigationUi();

        viewModel.connectWebSocket();

    }

    private void setUpToolbarWithNavigationUi() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration);
    }

//    @Override
//    public void onConnected() {
//        Log.e(TAG, "CONNECTED");
//    }
//
//    @Override
//    public void onDisConnected() {
//        Log.e(TAG, "DISCONNECTED");
//    }
//
//    @Override
//    public void onRejected() {
//        Log.e(TAG, "CONNECTION REJECTED");
//    }
//
//    @Override
//    public void onFailed(Throwable t) {
//        Log.e(TAG, "FAILED" + t.getMessage());
//    }
//
//    @Override
//    public <T> void onReceived(T any) {
//        viewModel.getMessageMutableLiveData().postValue(new Message());
//    }

    @Override
    public void onBackPressed() {
        viewModel.disconnectWebSocket();
        super.onBackPressed();
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }

    public ConfigChatSocket getChatSocket() {
        return chatSocket;
    }
}
