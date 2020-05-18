package com.example.chatsdkimpldemo.ui.myclass;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentMyClassBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;
import com.example.mychatlibrary.data.models.response.myclass.MyClassResponse;

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

        activityViewModel.getMyClassRooms().observe(getViewLifecycleOwner(), this::updateList);

        observeMessage();
    }

    private void updateList(BaseResponse<MyClassResponse> response) {
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
        adapter = new MyClassAdapter(responseList);
        binding.rvMyClass.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvMyClass.setAdapter(adapter);
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
        return R.layout.fragment_my_class;
    }

    @Override
    protected Class<MyClassViewModel> initViewModel() {
        return MyClassViewModel.class;
    }

}
