package com.example.chatsdkimpldemo.utils;

import androidx.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

public class BindingHelper {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        view.setImageURI(imageUrl);
    }
}
