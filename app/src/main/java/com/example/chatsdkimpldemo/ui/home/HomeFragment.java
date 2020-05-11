package com.example.chatsdkimpldemo.ui.home;

import androidx.fragment.app.Fragment;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentHomeBinding;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.chatsdkimpldemo.ui.chatrooms.webinars.WebinarFragment;
import com.example.chatsdkimpldemo.ui.myclass.MyClassFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    @Override
    protected void initFragment() {
        setupTabAndPager();
        observeData();
        binding.ivBack.setOnClickListener(v -> {

        });
    }

    private void setupTabAndPager() {
        changeLightStatusBar(true, requireActivity());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyClassFragment());
        fragments.add(new WebinarFragment());
        List<String> titles = Arrays.asList("My Classroom", "Webinar");
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void observeData() {

    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> initViewModel() {
        return HomeViewModel.class;
    }

}
