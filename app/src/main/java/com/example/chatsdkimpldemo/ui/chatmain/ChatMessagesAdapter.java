package com.example.chatsdkimpldemo.ui.chatmain;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageBinding;
import com.example.mychatlibrary.data.local.AppSharedPrefManager;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SENDER_TYPE = 1;
    private static final int RECEIVER_TYPE = 2;
    private List<Message> messageList;

    ChatMessagesAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SENDER_TYPE:
                ItemSenderMessageBinding senderMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_message, parent, false);
                return new SenderViewHolder(senderMessageBinding);
            case RECEIVER_TYPE:
            default:
                ItemReceiveMessageBinding receiverMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_message, parent, false);
                return new ReceiverViewHolder(receiverMessageBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).bind(messageList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getUserId() == AppSharedPrefManager.getUserId())
            return SENDER_TYPE;
        else
            return RECEIVER_TYPE;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        private ItemSenderMessageBinding senderMessageBinding;

        SenderViewHolder(ItemSenderMessageBinding senderMessageBinding) {
            super(senderMessageBinding.getRoot());
            this.senderMessageBinding = senderMessageBinding;
        }

        void bind(Message m) {
            senderMessageBinding.setMessage(m);
            senderMessageBinding.executePendingBindings();
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {

        private ItemReceiveMessageBinding receiveMessageBinding;

        ReceiverViewHolder(ItemReceiveMessageBinding receiveMessageBinding) {
            super(receiveMessageBinding.getRoot());
            this.receiveMessageBinding = receiveMessageBinding;
        }

        void bind(Message m) {
            receiveMessageBinding.setMessage(m);
            receiveMessageBinding.executePendingBindings();
        }
    }
}
