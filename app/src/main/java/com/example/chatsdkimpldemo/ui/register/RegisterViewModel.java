package com.example.chatsdkimpldemo.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatsdkimpldemo.domain.UsersRepository;
import com.example.mychatlibrary.data.models.request.register.RegisterRequestModel;
import com.example.mychatlibrary.data.models.response.register.RegisterResponse;

public class RegisterViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MediatorLiveData<RegisterResponse> data = new MediatorLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public void callRegister(RegisterRequestModel register) {
        _isLoading.postValue(true);
        data.addSource(repository.callRegister(register), registerResponse -> {
            _isLoading.postValue(false);
            data.postValue(registerResponse);
        });
    }

    public LiveData<RegisterResponse> getLoginResponseLiveData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
}
