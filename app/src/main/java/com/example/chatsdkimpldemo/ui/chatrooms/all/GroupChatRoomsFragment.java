package com.example.chatsdkimpldemo.ui.chatrooms.all;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentGroupChatBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.ui.chatrooms.GroupChatViewModel;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupChatRoomsFragment extends BaseFragment<FragmentGroupChatBinding, GroupChatViewModel> implements RecyclerViewItemClickListener<GroupChatResponse> {

    private List<GroupChatResponse> responseList;
    private GroupChatAdapter adapter;
    private NavController navController;
    private MainViewModel activityViewModel;

    @Override
    protected Class<GroupChatViewModel> initViewModel() {
        return GroupChatViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(GroupChatRoomsFragment.this);
        this.activityViewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();
        activityViewModel.getChatRooms();
        initAdapter();
        observeData();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new GroupChatAdapter(responseList, this);
        binding.rvAllGroupChat.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        binding.rvAllGroupChat.setAdapter(adapter);
    }

    private void observeData() {
        activityViewModel.getGroupChatResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList);
                adapter.notifyDataSetChanged();
            }
        });
        activityViewModel.getJoinChatRoomResponseLiveData().observe(getViewLifecycleOwner(), joinChatRoomResponse -> {
            if (joinChatRoomResponse != null) {
                Toast.makeText(getContext(), "Successfully joined!", Toast.LENGTH_LONG).show();
                navController.navigateUp();
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
        return R.layout.fragment_group_chat;
    }

    @Override
    public void onItemClick(View v, GroupChatResponse data, int position) {
        activityViewModel.joinChatRoom(data.getId());
    }
}
