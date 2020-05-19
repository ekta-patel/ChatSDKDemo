package com.example.chatsdkimpldemo.ui.chatroomdetail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemAddParticipantBinding;
import com.example.chatsdkimpldemo.databinding.ItemChatroomParticipantsBinding;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int NON_HEADER = 1;
    private List<User> participantsList;

    ParticipantAdapter(List<User> participantsList) {
        this.participantsList = participantsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            ItemAddParticipantBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_add_participant, parent, false);
            return new HeaderViewHolder(binding);
        } else {
            ItemChatroomParticipantsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chatroom_participants, parent, false);
            return new NonHeaderViewHolder(binding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return NON_HEADER;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind();
        } else {
            ((NonHeaderViewHolder) holder).bind(participantsList.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return participantsList.size() + 1;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ItemAddParticipantBinding binding;

        HeaderViewHolder(ItemAddParticipantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
        }
    }

    static class NonHeaderViewHolder extends RecyclerView.ViewHolder {

        private ItemChatroomParticipantsBinding binding;

        NonHeaderViewHolder(ItemChatroomParticipantsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }
    }
}
