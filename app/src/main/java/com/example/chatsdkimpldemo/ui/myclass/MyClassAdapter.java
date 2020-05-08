package com.example.chatsdkimpldemo.ui.myclass;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemMyClassBinding;
import com.example.chatsdkimpldemo.ui.home.HomeFragmentDirections;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.List;

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.ViewHolder> {

    private List<Chatroom> responseList;

    MyClassAdapter(List<Chatroom> list) {
        this.responseList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyClassBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_my_class, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(responseList.get(position));
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemMyClassBinding binding;

        ViewHolder(ItemMyClassBinding oneToOneChatBinding) {
            super(oneToOneChatBinding.getRoot());
            this.binding = oneToOneChatBinding;
            itemView.setOnClickListener(this);
        }

        void bind(Chatroom c) {
            binding.setChatroom(c);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.BundleKeys.IS_GROUP, false);
            User u = binding.getChatroom().getUser();
            bundle.putInt(Constants.BundleKeys.OTHER_USER_ID, u.getId());
            String name = TextUtils.isEmpty(u.getUsername()) ? (TextUtils.isEmpty(u.getEmail()) ? u.getPhoneNumber() : u.getEmail()) : u.getUsername();
            bundle.putString(Constants.BundleKeys.CHATROOM_NAME, name);
            NavDirections directions = HomeFragmentDirections.actionHomeFragmentToMainChatFragment(bundle);
            Navigation.findNavController(v).navigate(directions);
        }
    }
}
