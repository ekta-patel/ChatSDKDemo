package com.example.chatsdkimpldemo.ui.chatmain;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentMainChatBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.chatsdkimpldemo.utils.MarginItemDecorator;
import com.example.chatsdkimpldemo.utils.Utilities;
import com.example.mychatlibrary.ConfigChatSocket;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;

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
    private MediaRecorder recorder = null;
    private File audioFile = null;
    private File photoFile = null;

    @Override
    protected Class<MainChatViewModel> initViewModel() {
        return MainChatViewModel.class;
    }

    @SuppressLint("ClickableViewAccessibility")
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
            try {
                photoFile = Utilities.createImageFile(requireContext());
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.example.chatsdkimpldemo.fileprovider",
                        photoFile);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent chooserIntent = Intent.createChooser(intent, "Choose One");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent1});
            startActivityForResult(chooserIntent, Constants.RequestCodes.OPEN_CAMERA);
        });

        binding.ivBack.setOnClickListener(v -> navController.navigateUp());

        binding.ivRecordAudio.setOnTouchListener((v, event) -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        onRecord(false);
                        return true;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        onRecord(true);
                        return true;
                    }
                    default:
                        return false;
                }
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO}, Constants.PermissionCodes.RECORD_AUDIO);
            }
            return false;
        });

        initAdapter();

        observeData();
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        try {
            audioFile = Utilities.createAudioFile(requireContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                recorder.setOutputFile(audioFile);
            } else {
                recorder.setOutputFile(audioFile.getAbsolutePath());
            }
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        Uri uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.chatsdkimpldemo.fileprovider",
                audioFile);
        if (uri != null) {
            if (Objects.equals(uri.getScheme(), "content")) {
                sendMediaMessage(uri);
            }
        }
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
        activityViewModel.getMediaMessageResponseMediatorLiveData().observe(getViewLifecycleOwner(), mediaMessageResponse -> {
            if (!mediaMessageResponse.getStatus().equals("success")) {
                showSnackbar("something went wrong");
            }
        });
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
                            sendMediaMessage(uri);
                        }
                    }
                }
            } else if (requestCode == Constants.RequestCodes.OPEN_CAMERA) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        if (Objects.equals(uri.getScheme(), "content")) {
                            sendMediaMessage(uri);
                        }
                    }
                } else {
                    Uri uri = FileProvider.getUriForFile(requireContext(),
                            "com.example.chatsdkimpldemo.fileprovider",
                            photoFile);
                    if (uri != null) {
                        if (Objects.equals(uri.getScheme(), "content")) {
                            sendMediaMessage(uri);
                        }
                    }
                }
            }
        }
    }

    private void sendMediaMessage(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        String mimeType = contentResolver.getType(uri);
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        try {
            ParcelFileDescriptor fileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            File f = Utilities.createFileForExtension(requireContext(), extension);
            FileOutputStream outputStream = new FileOutputStream(f);
            Utilities.copy(inputStream, outputStream);
            activityViewModel.sendMediaMessage(mChatRoomId, f, MediaType.parse(Objects.requireNonNull(mimeType)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.PermissionCodes.RECORD_AUDIO:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                            Manifest.permission.RECORD_AUDIO)) {
                        showSnackbar("You can not record audio without permission");
                    }
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (photoFile != null) {
            outState.putSerializable(Constants.BundleKeys.PHOTO_FILE, photoFile);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            File f = (File) savedInstanceState.getSerializable(Constants.BundleKeys.PHOTO_FILE);
            if (f != null) {
                photoFile = f;
            }
        }
    }
}
