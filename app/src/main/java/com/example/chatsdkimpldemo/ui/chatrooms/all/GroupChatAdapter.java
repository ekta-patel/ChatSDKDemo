package com.example.chatsdkimpldemo.ui.chatrooms.all;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemGroupChatBinding;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;

import java.util.List;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.ViewHolder> {

    private List<GroupChatResponse> responseList;
    private RecyclerViewItemClickListener listener;

    GroupChatAdapter(List<GroupChatResponse> list, RecyclerViewItemClickListener listener) {
        this.responseList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGroupChatBinding groupChatBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_group_chat, parent, false);
        return new ViewHolder(groupChatBinding, listener);
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

        private ItemGroupChatBinding groupChatBinding;
        private RecyclerViewItemClickListener listener;

        ViewHolder(ItemGroupChatBinding groupChatBinding, RecyclerViewItemClickListener listener) {
            super(groupChatBinding.getRoot());
            this.groupChatBinding = groupChatBinding;
            this.listener = listener;
            View view = groupChatBinding.getRoot().findViewById(R.id.itemBtnJoin);
            view.setOnClickListener(this);
        }

        void bind(GroupChatResponse s) {
            groupChatBinding.setGroup(s);
            groupChatBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, groupChatBinding.getGroup(), getAdapterPosition());
        }
    }
}
