package com.example.mychatlibrary.data.remote;

import android.content.Context;

import com.example.mychatlibrary.utils.NoInternetException;
import com.example.mychatlibrary.utils.Utilities;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (!Utilities.hasNetwork(context)) {
            throw new NoInternetException("Internet may not be available");
        }

        return chain.proceed(chain.request());
    }
}
