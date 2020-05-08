package com.example.chatsdkimpldemo.ui.chatrooms.webinars;

import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentWebinarBinding;
import com.example.chatsdkimpldemo.databinding.LayoutDialogCreateroomBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.chatrooms.GroupChatViewModel;
import com.example.mychatlibrary.data.models.request.createchatroom.Chatroom;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;

import java.util.ArrayList;
import java.util.List;

public class WebinarFragment extends BaseFragment<FragmentWebinarBinding, GroupChatViewModel> {

    private List<com.example.mychatlibrary.data.models.response.chatroom.Chatroom> responseList;
    private WebinarAdapter adapter;
    private NavController navController;
    private MainViewModel activityViewModel;

    @Override
    protected Class<GroupChatViewModel> initViewModel() {
        return GroupChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(WebinarFragment.this);
        this.activityViewModel = ((MainActivity) requireActivity()).getViewModel();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
        adapter = new WebinarAdapter(responseList);
        binding.rvWebinar.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvWebinar.setAdapter(adapter);
    }

    private void observeData() {
        activityViewModel.getJoinedChatRoomResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList.getChatrooms());
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
        activityViewModel.getMessageMutableLiveData().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
//                    responseList.add(message);
                adapter.notifyItemInserted(adapter.getItemCount());
            }
        });
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_webinar;
    }

}
