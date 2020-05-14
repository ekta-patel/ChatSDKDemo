package com.example.chatsdkimpldemo.ui.myclass;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentMyClassBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;

import java.util.ArrayList;
import java.util.List;

public class MyClassFragment extends BaseFragment<FragmentMyClassBinding, MyClassViewModel> {

    private List<Chatroom> responseList;
    private MyClassAdapter adapter;
    private MainViewModel activityViewModel;

    @Override
    protected void initFragment() {
        this.activityViewModel = ((MainActivity) requireActivity()).getViewModel();
        binding.cardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_createChatroomFragment));
        initAdapter();

        observeData();

        activityViewModel.getOneToOneChatRooms();
    }

    private void initAdapter() {
        responseList = new ArrayList<>();
        adapter = new MyClassAdapter(responseList);
        binding.rvMyClass.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvMyClass.setAdapter(adapter);
    }

    private void observeData() {
        activityViewModel.getOneToOneChatResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
            this.responseList.clear();
            this.responseList.addAll(resList.getChatrooms());
            adapter.notifyDataSetChanged();
        });
        activityViewModel.getError().observe(getViewLifecycleOwner(), e -> {
            showSnackbar(e.getMessage());
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
        return R.layout.fragment_my_class;
    }

    @Override
    protected Class<MyClassViewModel> initViewModel() {
        return MyClassViewModel.class;
    }

}
