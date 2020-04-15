package com.example.chatsdkimpldemo.domain;

import androidx.lifecycle.MutableLiveData;

import com.example.chatsdkimpldemo.data.remote.ApiClient;
import com.example.chatsdkimpldemo.data.remote.ApiService;
import com.example.mychatlibrary.data.models.request.login.LoginRequest;
import com.example.mychatlibrary.data.models.request.logout.LogoutRequest;
import com.example.mychatlibrary.data.models.request.register.RegisterRequestModel;
import com.example.mychatlibrary.data.models.response.login.LoginResponse;
import com.example.mychatlibrary.data.models.response.logout.LogoutResponse;
import com.example.mychatlibrary.data.models.response.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository {

    private static UsersRepository INSTANCE;
    private ApiService apiService = ApiClient.getApiService();

    public static UsersRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepository();
        }
        return INSTANCE;
    }

    public MutableLiveData<LoginResponse> callLogin(LoginRequest request) {
        MutableLiveData<LoginResponse> data = new MutableLiveData<>();
        apiService.callLogin(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<RegisterResponse> callRegister(RegisterRequestModel register) {
        MutableLiveData<RegisterResponse> data = new MutableLiveData<>();
        apiService.callRegister(register).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<String> callLogout(LogoutRequest request) {
        MutableLiveData<String> data = new MutableLiveData<>();
        apiService.callLogout(request).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful())
                    data.postValue("success");
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }
}
