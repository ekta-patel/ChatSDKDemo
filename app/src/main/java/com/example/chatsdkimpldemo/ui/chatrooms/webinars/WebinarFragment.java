package com.example.chatsdkimpldemo.ui.chatrooms.webinars;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentWebinarBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.chatrooms.GroupChatViewModel;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;
import com.example.mychatlibrary.data.models.response.webinar.WebinarResponse;

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
        binding.btnViewChatRoom.setOnClickListener((v) -> navController.navigate(R.id.groupChatRoomsFragment));
        initAdapter();

        activityViewModel.getJoinedChatRooms().observe(getViewLifecycleOwner(), this::updateList);

        observeMessage();
    }

    private void updateList(BaseResponse<WebinarResponse> response) {
        switch (response.getStatus()) {
            case LOADING:
                showLoader();
                break;
            case SUCCESS:
                dismissLoader();
                this.responseList.clear();
                this.responseList.addAll(response.getData().getChatrooms());
                adapter.notifyDataSetChanged();
                break;
            case FAILURE:
                dismissLoader();
                showSnackbar(response.getThrowable().getMessage());
                break;
        }
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new WebinarAdapter(responseList);
        binding.rvWebinar.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvWebinar.setAdapter(adapter);
    }

    private void observeMessage() {
        activityViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                for (Chatroom x :
                        responseList) {
                    if (x.getId() == message.getChatroomId()) {
                        int index = responseList.indexOf(x);
                        x.setMessage(message);
                        adapter.notifyItemChanged(index, x);
                    }
                }
            }
        });
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_webinar;
    }

}
