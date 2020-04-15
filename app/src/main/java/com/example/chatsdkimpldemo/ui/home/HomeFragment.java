package com.example.chatsdkimpldemo.ui.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentHomeBinding;
import com.example.chatsdkimpldemo.ui.activities.LoginActivity;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.chatrooms.joined.JoinedChatRoomsFragment;
import com.example.chatsdkimpldemo.ui.one2onechat.OneToOneChatFragment;
import com.example.mychatlibrary.data.local.AppSharedPrefManager;
import com.example.mychatlibrary.data.models.request.logout.LogoutRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    @Override
    protected void initFragment() {
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getString(R.string.app_name));
        NavController navController = NavHostFragment.findNavController(HomeFragment.this);

        setHasOptionsMenu(true);
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(new OneToOneChatFragment());
        fragments.add(new JoinedChatRoomsFragment());
        titles.add("One To One Chat");
        titles.add("Group Chat");
        binding.viewpager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragments, titles));

        observeData();
    }

    private void observeData() {
        viewModel.getLogoutResponseLiveData().observe(getViewLifecycleOwner(), s -> {
            if (!TextUtils.isEmpty(s) && s.equalsIgnoreCase("success")) {
                AppSharedPrefManager.clear();
                moveToLogin();
            }
        });
        viewModel.isLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                dismissLoader();
            }
        });
    }

    private void moveToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> initViewModel() {
        return HomeViewModel.class;
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> fragments;
        private final List<String> titles;

        MyPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = fragments;
            this.titles = titles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            callLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callLogout() {
        LogoutRequest request = new LogoutRequest();
        request.setToken(AppSharedPrefManager.getToken());
        viewModel.logout(request);
    }
}
