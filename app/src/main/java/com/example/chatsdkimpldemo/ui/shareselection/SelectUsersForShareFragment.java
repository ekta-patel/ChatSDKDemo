package com.example.chatsdkimpldemo.ui.shareselection;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentSelectUsersForShareBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.activities.MainViewModel;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.createchatroom.CreateChatroomViewModel;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectUsersForShareFragment extends BaseFragment<FragmentSelectUsersForShareBinding, CreateChatroomViewModel> {

    private List<Chatroom> responseList;
    private SelectUsersForShareAdapter adapter;
    private MainViewModel activityViewModel;
    private SelectionTracker<Chatroom> selectionTracker;
    private NavController navController;
    private Uri uri;
    private String text;
    private boolean isTextShared;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = requireArguments();
        isTextShared = b.getBoolean(Constants.BundleKeys.IS_TEXT_SHARED);
        if (isTextShared) {
            text = b.getString(Constants.BundleKeys.SHARED_TEXT);
        } else {
            uri = b.getParcelable(Constants.BundleKeys.SHARED_FILE_URI);
        }
    }

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
        binding.tvSend.setOnClickListener((v) ->
        {
            Selection<Chatroom> chatroomSelection = selectionTracker.getSelection();
            if (chatroomSelection.size() == 1) {
                Chatroom c = null;
                for (Chatroom x :
                        chatroomSelection) {
                    c = x;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.BundleKeys.IS_GROUP, false);
                User u = Objects.requireNonNull(c).getUser();
                bundle.putInt(Constants.BundleKeys.CHATROOM_ID, c.getId());
                bundle.putInt(Constants.BundleKeys.OTHER_USER_ID, u.getId());
                String name = TextUtils.isEmpty(u.getUsername()) ? (TextUtils.isEmpty(u.getEmail()) ? u.getPhoneNumber() : u.getEmail()) : u.getUsername();
                bundle.putString(Constants.BundleKeys.OTHER_USER_NAME, name);
                bundle.putBoolean(Constants.BundleKeys.IS_TEXT_SHARED, isTextShared);
                if (isTextShared) {
                    bundle.putString(Constants.BundleKeys.SHARED_TEXT, text);
                } else {
                    bundle.putParcelable(Constants.BundleKeys.SHARED_FILE_URI, uri);
                }
                bundle.putBoolean(Constants.BundleKeys.IS_FROM_SHARING, true);
                NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.selectUsersForShareFragment, true).build();
                NavDirections directions = SelectUsersForShareFragmentDirections.actionSelectUsersForShareFragmentToMainChatFragment(bundle);
                navController.navigate(directions, options);
            } else {
                showSnackbar("Choose a member to share");
            }
        });
//        activityViewModel.getOneToOneChatRooms();
        binding.ivBack.setOnClickListener(v -> navController.navigateUp());
    }

    private void intiAdapter() {
        responseList = new ArrayList<>();
        binding.rvCreateChatroomUsers.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new SelectUsersForShareAdapter(responseList);
        binding.rvCreateChatroomUsers.setAdapter(adapter);
        selectionTracker = new SelectionTracker.Builder<>("hello_world",
                binding.rvCreateChatroomUsers,
                new SelectUsersForShareAdapter.KeyProvider(responseList),
                new SelectUsersForShareAdapter.DetailsLookup(binding.rvCreateChatroomUsers),
                StorageStrategy.createParcelableStorage(Chatroom.class))
                .withSelectionPredicate(new SelectUsersForShareAdapter.Predicate())
                .build();
        adapter.setSelectionTracker(selectionTracker);
    }

    private void observeData() {
//        activityViewModel.getOneToOneChatResponseLiveData().observe(getViewLifecycleOwner(), resList -> {
//            this.responseList.clear();
//            this.responseList.addAll(resList.getChatrooms());
//            adapter.notifyDataSetChanged();
//        });
//        activityViewModel.getError().observe(getViewLifecycleOwner(), e -> {
//            showSnackbar(e.getMessage());
//        });
//        activityViewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                showLoader();
//            } else {
//                dismissLoader();
//            }
//        });
        activityViewModel.getMyClassRooms().observe(getViewLifecycleOwner(), myClasses -> {
            switch (myClasses.getStatus()) {
                case LOADING:
                    showLoader();
                    break;
                case SUCCESS:
                    dismissLoader();
                    this.responseList.clear();
                    this.responseList.addAll(myClasses.getData().getChatrooms());
                    adapter.notifyDataSetChanged();
                    break;
                case FAILURE:
                    dismissLoader();
                    showSnackbar(myClasses.getThrowable().getMessage());
                    break;
            }
        });
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_select_users_for_share;
    }

}
