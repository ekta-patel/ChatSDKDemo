package com.example.chatsdkimpldemo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatsdkimpldemo.domain.UsersRepository;
import com.example.mychatlibrary.data.models.request.logout.LogoutRequest;

public class HomeViewModel extends ViewModel {

    private UsersRepository repository = UsersRepository.getInstance();
    private MediatorLiveData<String> data = new MediatorLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public void logout(LogoutRequest request) {
        _isLoading.postValue(true);
        data.addSource(repository.callLogout(request), loginResponse -> {
            _isLoading.postValue(false);
            data.postValue(loginResponse);
        });
    }

    public LiveData<String> getLogoutResponseLiveData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
}
