package com.example.chatsdkimpldemo.ui.createchatroom.create;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentSelectedUsersBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.ui.createchatroom.CreateChatroomViewModel;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomDataRequest;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectedUsersFragment extends BaseFragment<FragmentSelectedUsersBinding, CreateChatroomViewModel> implements RecyclerViewItemClickListener<User> {

    private List<User> selectedUserList;
    private SelectedUsersAdapter adapter;
    private MainViewModel activityViewModel;
    private NavController navController;

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
    protected Class<CreateChatroomViewModel> initViewModel() {
        return CreateChatroomViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = Navigation.findNavController(view);
        this.activityViewModel = ((MainActivity) requireActivity()).getViewModel();
        changeLightStatusBar(false, requireActivity());
        binding.setSize(selectedUserList.size());
        intiAdapter();
        observeData();
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
                request.setChatRoomDataRequest(dataRequest);
                activityViewModel.createChatRoom(request);
            }
        });
        binding.ivBack.setOnClickListener(v -> navController.navigateUp());
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

    private void observeData() {
        activityViewModel.getCreateChatRoomResponseLiveData().observe(getViewLifecycleOwner(), res -> {
            if (res.getStatus().equals("success")) {
                NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.BundleKeys.IS_GROUP, true);
                bundle.putInt(Constants.BundleKeys.CHATROOM_ID, res.getChatroom().getId());
                bundle.putString(Constants.BundleKeys.CHATROOM_NAME, res.getChatroom().getName());
                navController.navigate(R.id.action_createChatroomNameFragment_to_mainChatFragment, new Bundle(), options);
            }
        });
        activityViewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                dismissLoader();
            }
        });
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
}