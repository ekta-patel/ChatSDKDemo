package com.example.chatsdkimpldemo.data.remote;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
//                .header("Authorization", "bearer " + AppSharedPrefManager.getToken())
                .header("Content-Type", "application/json");
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
