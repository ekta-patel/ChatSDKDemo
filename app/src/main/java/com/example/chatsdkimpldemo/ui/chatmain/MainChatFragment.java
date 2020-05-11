package com.example.chatsdkimpldemo.ui.chatmain;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_OPEN_DOCUMENT;
import static android.content.Intent.CATEGORY_OPENABLE;
import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;

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
        changeLightStatusBar(false, requireActivity());
        this.activityViewModel = ((MainActivity) requireActivity()).getViewModel();
        this.chatSocket = activityViewModel.getChatSocket();
        if (getArguments() != null) {
            Bundle b = getArguments().getBundle("my_args");
            if (b != null) {
                mChatRoomId = b.getInt(Constants.BundleKeys.CHATROOM_ID);
                if (b.getBoolean(Constants.BundleKeys.IS_GROUP)) {
                    setHasOptionsMenu(true);
                    activityViewModel.getChatRoomMessages(mChatRoomId);
                } else {
                    int mOtherUserId = b.getInt(Constants.BundleKeys.OTHER_USER_ID);
                    activityViewModel.getUserMessages(mOtherUserId);
                }
                binding.setUsername(b.containsKey(Constants.BundleKeys.CHATROOM_NAME) ? b.getString(Constants.BundleKeys.CHATROOM_NAME) : b.getString(Constants.BundleKeys.OTHER_USER_NAME));
            }
        }

        binding.btnSendMessage.setOnClickListener((v) -> {
            String msg = Objects.requireNonNull(binding.etMessage.getText()).toString();
            if (!TextUtils.isEmpty(msg)) {
                chatSocket.sendMessage(mChatRoomId, msg);
                binding.etMessage.setText(null);
            }
        });

        binding.ivAttachment.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setAction(ACTION_OPEN_DOCUMENT);
            intent.addCategory(CATEGORY_OPENABLE);
            intent.setType("*/*");
            intent.putExtra(EXTRA_ALLOW_MULTIPLE, false);
            startActivityForResult(intent, Constants.RequestCodes.OPEN_DOCUMENT);
        });

        binding.ivCamera.setOnClickListener((v) -> {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            Intent intent1 = new Intent();
            intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            Intent chooserIntent = Intent.createChooser(intent, "Open...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent1});
            startActivityForResult(chooserIntent, Constants.RequestCodes.OPEN_CAMERA);
        });

        binding.ivBack.setOnClickListener(v -> navController.navigateUp());

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
                if (res.getChatroom().getMessages() != null) {
                    this.messageList.addAll(res.getChatroom().getMessages());
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
        activityViewModel.getMessageMutableLiveData().observe(getViewLifecycleOwner(), message -> {
                    messageList.add(message);
                    adapter.notifyItemInserted(messageList.size());
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCodes.OPEN_DOCUMENT) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        if (Objects.equals(uri.getScheme(), "content")) {
                            String path = Objects.requireNonNull(uri.getPath());
                            ContentResolver contentResolver = requireContext().getContentResolver();
                            String mimeType = contentResolver.getType(uri);
                            Cursor returnCursor = contentResolver.query(uri, null, null, null, null);
                            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            returnCursor.moveToFirst();
                            String fileName = returnCursor.getString(nameIndex);
                            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                            returnCursor.close();
                            File f = new File(path);
                            chatSocket.sendMediaMessage(mChatRoomId, "", f, mimeType, extension, fileName);
                        }
                    }
                }
            } else if (requestCode == Constants.RequestCodes.OPEN_CAMERA) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        if (Objects.equals(uri.getScheme(), "content")) {
                            String path = Objects.requireNonNull(uri.getPath());
                            ContentResolver contentResolver = requireContext().getContentResolver();
                            String mimeType = contentResolver.getType(uri);
                            Cursor returnCursor = contentResolver.query(uri, null, null, null, null);
                            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            returnCursor.moveToFirst();
                            String fileName = returnCursor.getString(nameIndex);
                            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                            returnCursor.close();
                            File f = new File(path);
                            chatSocket.sendMediaMessage(mChatRoomId, "", f, mimeType, extension, fileName);
                        }
                    } else {

                    }
                }
            }
        }
    }
}
