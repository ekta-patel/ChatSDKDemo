package com.example.mychatlibrary.data.remote;


import com.example.mychatlibrary.utils.MD5;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = null;
        try {
            requestBuilder = original.newBuilder()
                    .header("login", "useremail@gmail.com")
                    .header("password", MD5.getMd5("Tatva@123"))
                    .header("Accept", "*/*")
                    .header("Content-Type", "application/json; charset=utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
