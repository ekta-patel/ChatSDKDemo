package com.example.chatsdkimpldemo.ui.base;

import android.view.View;

public interface RecyclerViewItemClickListener<T> {

    void onItemClick(View v, T data, int position);
}