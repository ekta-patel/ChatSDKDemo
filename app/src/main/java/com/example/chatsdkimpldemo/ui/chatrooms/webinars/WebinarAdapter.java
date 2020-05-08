package com.example.chatsdkimpldemo.ui.chatrooms.webinars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemWebinarBinding;
import com.example.chatsdkimpldemo.ui.home.HomeFragmentDirections;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;

import java.util.List;

public class WebinarAdapter extends RecyclerView.Adapter<WebinarAdapter.ViewHolder> {

    private List<Chatroom> responseList;

    WebinarAdapter(List<Chatroom> list) {
        this.responseList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWebinarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_webinar, parent, false);
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

        private ItemWebinarBinding binding;

        ViewHolder(ItemWebinarBinding groupChatBinding) {
            super(groupChatBinding.getRoot());
            this.binding = groupChatBinding;
            itemView.setOnClickListener(this);
        }

        void bind(Chatroom c) {
            binding.setChatroom(c);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.BundleKeys.IS_GROUP, true);
            bundle.putInt(Constants.BundleKeys.CHATROOM_ID, binding.getChatroom().getId());
            bundle.putString(Constants.BundleKeys.CHATROOM_NAME, binding.getChatroom().getName());
            NavDirections directions = HomeFragmentDirections.actionHomeFragmentToMainChatFragment(bundle);
            Navigation.findNavController(v).navigate(directions);
        }
    }
}
