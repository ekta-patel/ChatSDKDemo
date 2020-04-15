package com.example.chatsdkimpldemo.ui.chatmain;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentMainChatBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.chatsdkimpldemo.utils.MarginItemDecorator;
import com.example.mychatlibrary.ConfigChatSocket;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainChatFragment extends BaseFragment<FragmentMainChatBinding, MainChatViewModel> {

    private static final String TAG = MainChatFragment.class.getSimpleName();

    private int mChatRoomId;
    private NavController navController;
    private ChatMessagesAdapter adapter;
    private List<Message> messageList;
    private ConfigChatSocket chatSocket;
    private MainViewModel activityViewModel;

    @Override
    protected Class<MainChatViewModel> initViewModel() {
        return MainChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(MainChatFragment.this);
        this.chatSocket = ((MainActivity) Objects.requireNonNull(getActivity())).getChatSocket();
        this.activityViewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();
        if (getArguments() != null) {
            Bundle b = getArguments().getBundle("my_args");
            if (b != null) {
                if (b.getBoolean(Constants.BundleKeys.IS_GROUP)) {
                    mChatRoomId = b.getInt(Constants.BundleKeys.CHATROOM_ID);
                    setHasOptionsMenu(true);
                    activityViewModel.getChatRoomMessages(mChatRoomId);
                } else {
                    int mOtherUserId = b.getInt(Constants.BundleKeys.OTHER_USER_ID);
                    mChatRoomId = b.getInt(Constants.BundleKeys.CHATROOM_ID);
                    activityViewModel.getUserMessages(mOtherUserId);
                }
                ActionBar actionbar = Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar());
                actionbar.setDisplayHomeAsUpEnabled(true);
                actionbar.setTitle(b.containsKey(Constants.BundleKeys.CHATROOM_NAME) ? b.getString(Constants.BundleKeys.CHATROOM_NAME) : getString(R.string.app_name));
            }
        }

        binding.fabSendMessage.setOnClickListener((v) -> {
            String msg = Objects.requireNonNull(binding.tieMessage.getText()).toString();
            if (!TextUtils.isEmpty(msg)) {
                chatSocket.sendMessage(1, msg, false, null);
            }
        });

        initAdapter();

        observeData();
    }

    private void initAdapter() {
        messageList = new ArrayList<>();
        adapter = new ChatMessagesAdapter(messageList);
        binding.rvMessages.addItemDecoration(new MarginItemDecorator(16, 16, 16, 16));
        binding.rvMessages.setAdapter(adapter);
    }

    private void observeData() {
        activityViewModel.getDeleteChatroomLiveData().observe(getViewLifecycleOwner(), deleteChatRoomResponse ->
                {
                    if (deleteChatRoomResponse.getStatus().equalsIgnoreCase("ok"))
                        navController.navigateUp();
                }
        );
        activityViewModel.getLeaveChatroomLiveData().observe(getViewLifecycleOwner(), leaveChatroomResponse -> {
            if (leaveChatroomResponse.getSuccess().toLowerCase().contains("leave")) {
                navController.navigateUp();
            }
        });
        activityViewModel.getMessageResponseLiveData().observe(getViewLifecycleOwner(), res -> {
            if (res != null) {
                this.messageList.clear();
                if (res.getMessages() != null) {
                    this.messageList.addAll(res.getMessages());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        activityViewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                dismissLoader();
            }
        });
        activityViewModel.getMessageMutableLiveData().observe(getViewLifecycleOwner(), message ->
                Log.e(TAG, "Hello World")
        );
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_main_chat;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteChatRoom();
                return true;
            case R.id.action_leave:
                leaveChatRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void leaveChatRoom() {
        activityViewModel.leaveChatroom(mChatRoomId);
    }

    private void deleteChatRoom() {
        activityViewModel.deleteChatroom(mChatRoomId);
    }

}
