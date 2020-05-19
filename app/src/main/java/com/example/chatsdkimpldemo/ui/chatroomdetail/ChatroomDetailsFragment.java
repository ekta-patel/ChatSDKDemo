package com.example.chatsdkimpldemo.ui.chatroomdetail;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentChatroomDetailsBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.chatsdkimpldemo.utils.MarginItemDecorator;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.chatroomdetails.ChatroomDetailsResponseModel;
import com.example.mychatlibrary.data.models.response.messages.Message;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.ArrayList;
import java.util.List;

public class ChatroomDetailsFragment extends BaseFragment<FragmentChatroomDetailsBinding, ChatroomDetailsViewModel> {

    private NavController navController;
    private List<User> participantList;
    private List<Message> mediaList;
    private MediaAdapter mediaAdapter;
    private ParticipantAdapter participantAdapter;


    @Override
    protected Class initViewModel() {
        return ChatroomDetailsViewModel.class;
    }

    @Override
    protected void initFragment() {
        MainViewModel activityViewModel = ((MainActivity) requireActivity()).getViewModel();
        navController = Navigation.findNavController(view);
        changeLightStatusBar(false, requireActivity());
        binding.setIsGroup(requireArguments().getBoolean(Constants.BundleKeys.IS_GROUP));
        activityViewModel.getChatroomDetails(requireArguments().getInt(Constants.BundleKeys.CHATROOM_ID, -1)).observe(getViewLifecycleOwner(), this::updateData);

        binding.ivBack.setOnClickListener((v) -> navController.navigateUp());

        intiAdapter();
    }

    private void updateData(BaseResponse<ChatroomDetailsResponseModel> response) {
        switch (response.getStatus()) {
            case SUCCESS:
                dismissLoader();
                binding.setChatroom(response.getData().getChatroom());
                if (response.getData().getChatroom().getUsers() != null) {
                    this.participantList.addAll(response.getData().getChatroom().getUsers());
                    participantAdapter.notifyDataSetChanged();
                }
                this.mediaList.addAll(response.getData().getChatroom().getMessages());
                mediaAdapter.notifyDataSetChanged();
                break;
            case LOADING:
                showLoader();
                break;
            case FAILURE:
                dismissLoader();
                break;
        }
    }

    private void intiAdapter() {
        this.participantList = new ArrayList<>();
        binding.rvParticipants.addItemDecoration(new MarginItemDecorator(0, 0, getResources().getDimensionPixelOffset(R.dimen.min_margin), getResources().getDimensionPixelOffset(R.dimen.min_margin)));
        participantAdapter = new ParticipantAdapter(participantList);
        binding.rvParticipants.setAdapter(participantAdapter);
        this.mediaList = new ArrayList<>();
        binding.rvMedia.addItemDecoration(new MarginItemDecorator(0, getResources().getDimensionPixelOffset(R.dimen.normal_margin), 0, 0));
        mediaAdapter = new MediaAdapter(this.mediaList);
        binding.rvMedia.setAdapter(mediaAdapter);
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_chatroom_details;
    }
}
