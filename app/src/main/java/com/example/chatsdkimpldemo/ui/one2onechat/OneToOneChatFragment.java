package com.example.chatsdkimpldemo.ui.one2onechat;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentOnetooneChatBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.ui.home.HomeFragmentDirections;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.actioncables.Message;
import com.example.mychatlibrary.data.models.response.one2onechat.OneToOneChatDataResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OneToOneChatFragment extends BaseFragment<FragmentOnetooneChatBinding, OneToOneViewModel> implements RecyclerViewItemClickListener<OneToOneChatDataResponse> {

    private List<OneToOneChatDataResponse> responseList;
    private OneToOneChatAdapter adapter;
    private NavController navController;
    private MainViewModel activityViewModel;

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(OneToOneChatFragment.this);
        this.activityViewModel = ((MainActivity) Objects.requireNonNull(getActivity())).getViewModel();
        initAdapter();

        observeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        activityViewModel.getOneToOneChatRooms();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new OneToOneChatAdapter(responseList, this);
        binding.rvOneToOneChat.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        binding.rvOneToOneChat.setAdapter(adapter);
    }

    private void observeData() {
        activityViewModel.getOneToOneChatResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList.getData());
                adapter.notifyDataSetChanged();
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
        return R.layout.fragment_onetoone_chat;
    }

    @Override
    protected Class<OneToOneViewModel> initViewModel() {
        return OneToOneViewModel.class;
    }

    @Override
    public void onItemClick(View v, OneToOneChatDataResponse data, int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.BundleKeys.IS_GROUP, false);
        bundle.putInt(Constants.BundleKeys.OTHER_USER_ID, data.getUser().getId());
        bundle.putString(Constants.BundleKeys.CHATROOM_NAME, data.getUser().getUsername());
        NavDirections directions = HomeFragmentDirections.actionHomeFragmentToMainChatFragment(bundle);
        navController.navigate(directions);
    }
}
