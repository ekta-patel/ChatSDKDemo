package com.example.chatsdkimpldemo.ui.createchatroom.selectuser;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentSelectUsersBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.createchatroom.CreateChatroomViewModel;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.ArrayList;
import java.util.List;

public class SelectUsersFragment extends BaseFragment<FragmentSelectUsersBinding, CreateChatroomViewModel> {


    private List<Chatroom> responseList;
    private SelectUsersAdapter adapter;
    private MainViewModel activityViewModel;
    private SelectionTracker<Chatroom> selectionTracker;
    private NavController navController;

    @Override
    protected Class<CreateChatroomViewModel> initViewModel() {
        return CreateChatroomViewModel.class;
    }

    @Override
    protected void initFragment() {
        this.activityViewModel = ((MainActivity) requireActivity()).getViewModel();
        navController = Navigation.findNavController(view);
        changeLightStatusBar(false, requireActivity());
        intiAdapter();
        observeData();
        binding.tvNext.setOnClickListener((v) ->
        {
            Selection<Chatroom> chatroomSelection = selectionTracker.getSelection();
            if (chatroomSelection.size() >= 2) {
                ArrayList<User> uList = new ArrayList<>();
                for (Chatroom c :
                        chatroomSelection) {
                    uList.add(c.getUser());
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.BundleKeys.SELECTED_USERS, uList);
                navController.navigate(R.id.action_createChatroomFragment_to_createChatroomNameFragment, bundle);
            } else {
                showSnackbar("At least 2 members needed to create a group");
            }
        });
        activityViewModel.getOneToOneChatRooms();
        binding.tvSelectAll.setOnClickListener((v) -> selectionTracker.setItemsSelected(() -> responseList.listIterator(), true));
        binding.ivBack.setOnClickListener(v -> navController.navigateUp());
    }

    private void intiAdapter() {
        responseList = new ArrayList<>();
        binding.rvCreateChatroomUsers.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new SelectUsersAdapter(responseList);
        binding.rvCreateChatroomUsers.setAdapter(adapter);
        selectionTracker = new SelectionTracker.Builder<>("hello_world",
                binding.rvCreateChatroomUsers,
                new SelectUsersAdapter.KeyProvider(responseList),
                new SelectUsersAdapter.DetailsLookup(binding.rvCreateChatroomUsers),
                StorageStrategy.createParcelableStorage(Chatroom.class))
                .withSelectionPredicate(new SelectUsersAdapter.Predicate())
                .build();
        adapter.setSelectionTracker(selectionTracker);
    }

    private void observeData() {
        activityViewModel.getOneToOneChatResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            if (resList != null) {
                this.responseList.clear();
                this.responseList.addAll(resList.getChatrooms());
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
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_select_users;
    }

}
