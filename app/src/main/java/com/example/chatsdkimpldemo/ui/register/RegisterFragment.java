package com.example.chatsdkimpldemo.ui.register;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.FragmentRegisterBinding;
import com.example.chatsdkimpldemo.ui.base.BaseFragment;
import com.example.mychatlibrary.data.models.request.register.RegisterRequest;
import com.example.mychatlibrary.data.models.request.register.RegisterRequestModel;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {

    private NavController navController;

    @Override
    protected Class<RegisterViewModel> initViewModel() {
        return RegisterViewModel.class;
    }

    @Override
    protected void initFragment() {
        navController = NavHostFragment.findNavController(RegisterFragment.this);
        binding.setRegister(new RegisterRequest());
        binding.btnRegister.setOnClickListener((v) -> {
            if (isValid()) {
                RegisterRequestModel requestModel = new RegisterRequestModel();
                requestModel.setRegisterRequest(binding.getRegister());
                viewModel.callRegister(requestModel);
            }
        });

        observeData();
    }

    private void observeData() {
        viewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), registerResponse -> {
            if (registerResponse != null)
                navController.popBackStack();
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
        String username = binding.getRegister().getUsername();
        String email = binding.getRegister().getEmail();
        String password = binding.getRegister().getPassword();
        String confirmPassword = binding.getRegister().getPasswordConfirmation();

        if (TextUtils.isEmpty(email) || TextUtils.getTrimmedLength(email) <= 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            binding.etEmail.setError("Enter valid email");
        }
        if (TextUtils.isEmpty(password) || TextUtils.getTrimmedLength(password) <= 0) {
            isValid = false;
            binding.etPassword.setError("Enter valid password");
        }
        if (TextUtils.isEmpty(username) || TextUtils.getTrimmedLength(username) <= 0) {
            isValid = false;
            binding.etUsername.setError("Enter valid username");
        }
        if (TextUtils.isEmpty(confirmPassword) || TextUtils.getTrimmedLength(confirmPassword) <= 0 || !confirmPassword.equals(password)) {
            isValid = false;
            binding.etConfirmPassword.setError("Enter valid confirm password");
        }

        return isValid;

    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_register;
    }
}
