package com.example.mychatlibrary.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mychatlibrary.data.models.response.login.LoginResponse;
import com.example.mychatlibrary.utils.Constants;
import com.google.gson.Gson;

public class AppSharedPrefManager {

    private static SharedPreferences PREFERENCE;

    public static void initInstance(Context context) {
        if (PREFERENCE == null) {
            PREFERENCE = context.getSharedPreferences(
                    Constants.SharedPrefName, Context.MODE_PRIVATE);
        }
    }

    public static String getToken() {
        String str = PREFERENCE.getString(Constants.SharedPrefKeys.LOGIN_RESPONSE, "");
        LoginResponse response = new Gson().fromJson(str, LoginResponse.class);
        String token;
        if (response == null) {
            token = null;
        } else {
            token = response.getAccessToken();
        }
        return token;
    }

    public static int getUserId() {
        String str = PREFERENCE.getString(Constants.SharedPrefKeys.LOGIN_RESPONSE, "");
        LoginResponse response = new Gson().fromJson(str, LoginResponse.class);
        int userId;
        if (response == null) {
            userId = 0;
        } else {
            userId = response.getUserId();
        }
        return userId;
    }

    public static void setLoginData(String key, LoginResponse value) {
        SharedPreferences.Editor editor = PREFERENCE.edit();
        String str = new Gson().toJson(value);
        editor.putString(key, str);
        editor.apply();
    }

    public static void clear() {
        PREFERENCE.edit().clear().apply();
    }
}
