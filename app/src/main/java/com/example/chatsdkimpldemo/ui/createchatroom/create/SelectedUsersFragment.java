package com.example.chatsdkimpldemo.ui.createchatroom.create;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentSelectedUsersBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.chatsdkimpldemo.utils.Utilities;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomDataRequest;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;

public class SelectedUsersFragment extends BaseFragment<FragmentSelectedUsersBinding> implements RecyclerViewItemClickListener<User> {

    private List<User> selectedUserList;
    private SelectedUsersAdapter adapter;
    private MainViewModel activityViewModel;
    private NavController navController;
    private File photoFile = null;
    private File groupIcon = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            List<User> users = getArguments().getParcelableArrayList(Constants.BundleKeys.SELECTED_USERS);
            selectedUserList = new ArrayList<>();
            if (users != null) {
                selectedUserList.addAll(users);
            }
        }
    }

    @Override
    protected void initFragment() {
        navController = Navigation.findNavController(view);
        this.activityViewModel = ((MainActivity) requireActivity()).getViewModel();
        changeLightStatusBar(false, requireActivity());
        binding.setSize(selectedUserList.size());
        intiAdapter();
        binding.tvCreate.setOnClickListener((v) -> {
            if (isValid()) {
                CreateChatRoomRequest request = new CreateChatRoomRequest();
                CreateChatRoomDataRequest dataRequest = new CreateChatRoomDataRequest();
                dataRequest.setName(Objects.requireNonNull(binding.tieGroupName.getText()).toString().trim());
                List<Integer> iList = new ArrayList<>();
                for (User u :
                        selectedUserList) {
                    iList.add(u.getId());
                }
                dataRequest.setUserIds(iList);
                if (groupIcon != null) {
                    dataRequest.setGroupImage(groupIcon);
                }
                request.setChatRoomDataRequest(dataRequest);
                activityViewModel.createChatRoom(request).observe(getViewLifecycleOwner(), this::handleResponse);
            }
        });
        binding.ivBack.setOnClickListener(v -> navController.navigateUp());
        binding.ivGroup.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(EXTRA_ALLOW_MULTIPLE, false);
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
            Intent chooserIntent = Intent.createChooser(intent1, "Choose image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
            startActivityForResult(chooserIntent, Constants.RequestCodes.OPEN_CAMERA);
        });
    }

    private void handleResponse(BaseResponse<CreateChatroomResponse> response) {
        switch (response.getStatus()) {
            case LOADING:
                showLoader();
                break;
            case SUCCESS:
                dismissLoader();
                Map<String, String> m = new HashMap<>();
                m.put("chatrooms", String.valueOf(response.getData().getChatroom().getId()));
                activityViewModel.connectWebSocket(m);
                NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.BundleKeys.IS_GROUP, true);
                bundle.putInt(Constants.BundleKeys.CHATROOM_ID, response.getData().getChatroom().getId());
                bundle.putString(Constants.BundleKeys.CHATROOM_NAME, response.getData().getChatroom().getName());
                NavDirections directions = SelectedUsersFragmentDirections.actionCreateChatroomNameFragmentToMainChatFragment(bundle);
                navController.navigate(directions, options);
                break;
            case FAILURE:
                dismissLoader();
                showSnackbar(response.getThrowable().getMessage());
                break;
        }
    }

    private boolean isValid() {
        boolean valid = true;
        if (TextUtils.isEmpty(Objects.requireNonNull(binding.tieGroupName.getText()).toString())) {
            valid = false;
            binding.tilGroupName.setError("Enter group name");
        }
        if (selectedUserList.size() < 2) {
            valid = false;
            showSnackbar("At least 2 members needed to create a group");
        }
        return valid;
    }

    private void intiAdapter() {
        adapter = new SelectedUsersAdapter(selectedUserList, SelectedUsersFragment.this);
        binding.rvSelectedUsers.setAdapter(adapter);
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_selected_users;
    }

    @Override
    public void onItemClick(View v, User data, int position) {
        selectedUserList.remove(data);
        adapter.notifyItemRemoved(position);
        binding.setSize(selectedUserList.size());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCodes.OPEN_CAMERA) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        if (Objects.equals(uri.getScheme(), ContentResolver.SCHEME_CONTENT)) {
                            Glide.with(requireContext()).load(uri).transform(new CenterCrop(), new CircleCrop()).into(binding.ivGroup);
                            setPhotoFile(uri);
                        }
                    }
                } else {
                    Uri uri1 = FileProvider.getUriForFile(requireContext(),
                            "com.example.chatsdkimpldemo.fileprovider",
                            photoFile);
                    if (uri1 != null) {
                        if (Objects.equals(uri1.getScheme(), ContentResolver.SCHEME_CONTENT)) {
                            Glide.with(requireContext()).load(uri1).transform(new CenterCrop(), new CircleCrop()).into(binding.ivGroup);
                            setPhotoFile(uri1);
                        }
                    }
                }
            }
        }
    }

    private void setPhotoFile(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        String mimeType = contentResolver.getType(uri);
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        try {
            ParcelFileDescriptor fileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            FileInputStream inputStream = new FileInputStream(Objects.requireNonNull(fileDescriptor).getFileDescriptor());
            File f = Utilities.createFileForExtension(requireContext(), extension);
            FileOutputStream outputStream = new FileOutputStream(f);
            Utilities.copy(inputStream, outputStream);
            groupIcon = f;
        } catch (IOException e) {
            e.printStackTrace();
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
