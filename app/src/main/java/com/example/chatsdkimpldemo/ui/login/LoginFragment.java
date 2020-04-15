package com.example.chatsdkimpldemo.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentLoginBinding;
import com.example.chatsdkimpldemo.ui.activities.MainActivity;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.mychatlibrary.data.local.AppSharedPrefManager;
import com.example.mychatlibrary.data.models.request.login.LoginRequest;
import com.example.mychatlibrary.utils.Constants;

import java.util.Objects;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    @Override
    protected Class<LoginViewModel> initViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void initFragment() {
        NavController navController = NavHostFragment.findNavController(LoginFragment.this);
        binding.setLogin(new LoginRequest());
        binding.btnLogin.setOnClickListener((v) -> {
            if (isValid()) viewModel.callLogin(binding.getLogin());
        });
        binding.btnRegister.setOnClickListener((v) -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));

        observeData();
    }

    private void observeData() {

        viewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), loginResponse -> {
            if (loginResponse != null) {
                AppSharedPrefManager.setLoginData(Constants.SharedPrefKeys.LOGIN_RESPONSE, loginResponse);
                startActivity(new Intent(getActivity(), MainActivity.class));
                Objects.requireNonNull(getActivity()).finish();
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

    private boolean isValid() {
        boolean isValid = true;
        String email = binding.getLogin().getEmail();
        String password = binding.getLogin().getPassword();
        if (TextUtils.isEmpty(email) || TextUtils.getTrimmedLength(email) <= 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            binding.etEmail.setError("Enter valid email");
        }
        if (TextUtils.isEmpty(password) || TextUtils.getTrimmedLength(password) <= 0) {
            isValid = false;
            binding.etPassword.setError("Enter valid password");
        }

        return isValid;
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_login;
    }

}
