package com.example.chatsdkimpldemo.utils;


import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingHelper {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        String x = "http://13.235.232.157" + imageUrl;
        view.setImageURI(x);
    }

    @BindingAdapter({"loadProfile"})
    public static void loadProfile(SimpleDraweeView view, int id) {
        view.setImageURI("https://cdn.clipart.email/34ef97566964eb0d43fa11c929d00f2c_avatar-pic-circle-png-picture-401512-avatar-pic-circle-png_512-512.png");
    }

    @BindingAdapter({"setTime"})
    public static void showTime(AppCompatTextView textView, String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = format.parse(datetime);
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm aa");
            String time = localDateFormat.format(date);
            textView.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
