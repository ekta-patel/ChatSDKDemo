package com.example.chatsdkimpldemo.ui.chatrooms.joined;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemJoinedGroupChatBinding;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.mychatlibrary.data.models.response.joinedgroups.JoinedChatRoomResponse;

import java.util.List;

public class JoinedGroupAdapter extends RecyclerView.Adapter<JoinedGroupAdapter.ViewHolder> {

    private List<JoinedChatRoomResponse> responseList;
    private RecyclerViewItemClickListener listener;

    JoinedGroupAdapter(List<JoinedChatRoomResponse> list, RecyclerViewItemClickListener listener) {
        this.responseList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJoinedGroupChatBinding groupChatBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_joined_group_chat, parent, false);
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

        private ItemJoinedGroupChatBinding groupChatBinding;
        private RecyclerViewItemClickListener listener;

        ViewHolder(ItemJoinedGroupChatBinding groupChatBinding, RecyclerViewItemClickListener listener) {
            super(groupChatBinding.getRoot());
            this.groupChatBinding = groupChatBinding;
            this.listener = listener;
            View view = groupChatBinding.getRoot();
            view.setOnClickListener(this);
        }

        void bind(JoinedChatRoomResponse s) {
            groupChatBinding.setGroup(s);
            groupChatBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, groupChatBinding.getGroup(), getAdapterPosition());
        }
    }
}
