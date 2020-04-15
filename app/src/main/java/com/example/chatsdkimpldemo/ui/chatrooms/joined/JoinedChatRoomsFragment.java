package com.example.chatsdkimpldemo.ui.chatrooms.joined;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentJoinedGroupChatBinding;
import com.example.chatsdkimpldemo.databinding.LayoutDialogCreateroomBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.ui.chatrooms.GroupChatViewModel;
import com.example.chatsdkimpldemo.ui.home.HomeFragmentDirections;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.actioncables.Message;
import com.example.mychatlibrary.data.models.request.createchatroom.Chatroom;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.joinedgroups.JoinedChatRoomResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JoinedChatRoomsFragment extends BaseFragment<FragmentJoinedGroupChatBinding, GroupChatViewModel> implements RecyclerViewItemClickListener<JoinedChatRoomResponse> {

    private List<JoinedChatRoomResponse> responseList;
    private JoinedGroupAdapter adapter;
    private NavController navController;
    private MainViewModel activityViewModel;

    @Override
    protected Class<GroupChatViewModel> initViewModel() {
        return GroupChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(JoinedChatRoomsFragment.this);
        this.activityViewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();
        binding.btnCreateChatRoom.setOnClickListener((v) -> openCreateChatDialog().show());
        binding.btnViewChatRoom.setOnClickListener((v) -> navController.navigate(R.id.groupChatRoomsFragment));
        initAdapter();

        observeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        activityViewModel.getJoinedChatRooms();
    }

    private AlertDialog openCreateChatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LayoutDialogCreateroomBinding dialogCreateroomBinding = DataBindingUtil.inflate(inflater, R.layout.layout_dialog_createroom, null, false);
        dialogCreateroomBinding.setCreateChatroom(new Chatroom());
        builder.setView(dialogCreateroomBinding.getRoot())
                .setPositiveButton("Create", (dialog, id) -> {
                    String chatRoomName = dialogCreateroomBinding.getCreateChatroom().getName();
                    if (!chatRoomName.trim().isEmpty()) {
                        CreateChatRoomRequest request = new CreateChatRoomRequest();
                        request.setChatroom(dialogCreateroomBinding.getCreateChatroom());
                        activityViewModel.createChatRoom(request);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        return builder.create();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new JoinedGroupAdapter(responseList, this);
        binding.rvJoinedGroupChat.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        binding.rvJoinedGroupChat.setAdapter(adapter);
    }

    private void observeData() {
        activityViewModel.getJoinedChatRoomResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList);
                adapter.notifyDataSetChanged();
            }
        });
        activityViewModel.getCreateChatRoomResponseLiveData().observe(getViewLifecycleOwner(), createChatroomResponse -> {
            if (createChatroomResponse != null) {
                CreateChatroomResponse c = createChatroomResponse.getContentIfNotHandledOrReturnNull();
                if (c != null) {
                    if (c.getStatus().equalsIgnoreCase("ok")) {
                        Toast.makeText(getContext(), "Chatroom created successfully", Toast.LENGTH_LONG).show();
                        activityViewModel.getJoinedChatRooms();
                    }
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
        activityViewModel.getMessageMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                if (message != null) {
//                    responseList.add(message);
                    adapter.notifyItemInserted(adapter.getItemCount());
                }
            }
        });
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_joined_group_chat;
    }

    @Override
    public void onItemClick(View v, JoinedChatRoomResponse data, int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.BundleKeys.IS_GROUP, true);
        bundle.putInt(Constants.BundleKeys.CHATROOM_ID, data.getId());
        bundle.putString(Constants.BundleKeys.CHATROOM_NAME, data.getName());
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToMainChatFragment(bundle);
        navController.navigate(directions);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
