package com.example.chatsdkimpldemo.ui.chatmain;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMediaBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMediaTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageTwoBinding;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SENDER_TYPE1 = 1;
    private static final int SENDER_TYPE2 = 2;
    private static final int RECEIVER_TYPE1 = 3;
    private static final int RECEIVER_TYPE2 = 4;
    private static final int SENDER_MEDIA_TYPE1 = 5;
    private static final int SENDER_MEDIA_TYPE2 = 6;
    private static final int RECEIVER_MEDIA_TYPE1 = 7;
    private static final int RECEIVER_MEDIA_TYPE2 = 8;

    private List<Message> messageList;

    public ChatMessagesAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SENDER_TYPE1:
                ItemSenderMessageBinding senderMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_message, parent, false);
                return new SenderViewHolder(senderMessageBinding);
            case RECEIVER_TYPE1:
                ItemReceiveMessageBinding receiverMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_message, parent, false);
                return new ReceiverViewHolder(receiverMessageBinding);
            case SENDER_TYPE2:
                ItemSenderMessageTwoBinding sender2MessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_message_two, parent, false);
                return new Sender2ViewHolder(sender2MessageBinding);
            case RECEIVER_TYPE2:
            default:
                ItemReceiveMessageTwoBinding receiver2MessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_message_two, parent, false);
                return new Receiver2ViewHolder(receiver2MessageBinding);
            case SENDER_MEDIA_TYPE1:
                ItemSenderMediaBinding senderMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media, parent, false);
                return new SenderMediaViewHolder(senderMediaBinding);
            case RECEIVER_MEDIA_TYPE1:
                ItemReceiveMediaBinding receiverMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_media, parent, false);
                return new ReceiverMediaViewHolder(receiverMediaBinding);
            case SENDER_MEDIA_TYPE2:
                ItemSenderMediaTwoBinding senderMedia2MessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_two, parent, false);
                return new SenderMedia2ViewHolder(senderMedia2MessageBinding);
            case RECEIVER_MEDIA_TYPE2:
                ItemReceiveMediaTwoBinding receiverMedia2MessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_media_two, parent, false);
                return new ReceiverMedia2ViewHolder(receiverMedia2MessageBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof Sender2ViewHolder) {
            ((Sender2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof Receiver2ViewHolder) {
            ((Receiver2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof SenderMediaViewHolder) {
            ((SenderMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverMediaViewHolder) {
            ((ReceiverMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof SenderMedia2ViewHolder) {
            ((SenderMedia2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverMedia2ViewHolder) {
            ((ReceiverMedia2ViewHolder) holder).bind(messageList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message m = messageList.get(position);
        if (m.getUserId() == 3) {
            if (TextUtils.isEmpty(m.getAttachment())) {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() == 3) {
                        return SENDER_TYPE2;
                    } else {
                        return SENDER_TYPE1;
                    }
                } else {
                    return SENDER_TYPE1;
                }
            } else {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() == 3) {
                        return SENDER_MEDIA_TYPE2;
                    } else {
                        return SENDER_MEDIA_TYPE1;
                    }
                } else {
                    return SENDER_MEDIA_TYPE1;
                }
            }
        } else {
            if (TextUtils.isEmpty(m.getAttachment())) {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() != 3) {
                        return RECEIVER_TYPE2;
                    } else {
                        return RECEIVER_TYPE1;
                    }
                } else {
                    return RECEIVER_TYPE1;
                }
            } else {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() != 3) {
                        return RECEIVER_MEDIA_TYPE2;
                    } else {
                        return RECEIVER_MEDIA_TYPE1;
                    }
                } else {
                    return RECEIVER_MEDIA_TYPE1;
                }
            }
        }
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

    static class Receiver2ViewHolder extends RecyclerView.ViewHolder {

        private ItemReceiveMessageTwoBinding receiveMessageBinding;

        Receiver2ViewHolder(ItemReceiveMessageTwoBinding receiveMessageBinding) {
            super(receiveMessageBinding.getRoot());
            this.receiveMessageBinding = receiveMessageBinding;
        }

        void bind(Message m) {
            receiveMessageBinding.setMessage(m);
            receiveMessageBinding.executePendingBindings();
        }
    }


    static class Sender2ViewHolder extends RecyclerView.ViewHolder {
        private ItemSenderMessageTwoBinding senderMessageBinding;

        Sender2ViewHolder(ItemSenderMessageTwoBinding senderMessageBinding) {
            super(senderMessageBinding.getRoot());
            this.senderMessageBinding = senderMessageBinding;
        }

        void bind(Message m) {
            senderMessageBinding.setMessage(m);
            senderMessageBinding.executePendingBindings();
        }
    }

    static class SenderMediaViewHolder extends RecyclerView.ViewHolder {
        private ItemSenderMediaBinding senderMediaBinding;

        SenderMediaViewHolder(ItemSenderMediaBinding senderMediaBinding) {
            super(senderMediaBinding.getRoot());
            this.senderMediaBinding = senderMediaBinding;
        }

        void bind(Message m) {
            senderMediaBinding.setMessage(m);
            senderMediaBinding.executePendingBindings();
        }
    }

    static class ReceiverMediaViewHolder extends RecyclerView.ViewHolder {

        private ItemReceiveMediaBinding receiveMediaBinding;

        ReceiverMediaViewHolder(ItemReceiveMediaBinding receiveMediaBinding) {
            super(receiveMediaBinding.getRoot());
            this.receiveMediaBinding = receiveMediaBinding;
        }

        void bind(Message m) {
            receiveMediaBinding.setMessage(m);
            receiveMediaBinding.executePendingBindings();
        }
    }

    static class ReceiverMedia2ViewHolder extends RecyclerView.ViewHolder {

        private ItemReceiveMediaTwoBinding receiveMediaBinding;

        ReceiverMedia2ViewHolder(ItemReceiveMediaTwoBinding receiveMediaBinding) {
            super(receiveMediaBinding.getRoot());
            this.receiveMediaBinding = receiveMediaBinding;
        }

        void bind(Message m) {
            receiveMediaBinding.setMessage(m);
            receiveMediaBinding.executePendingBindings();
        }
    }


    static class SenderMedia2ViewHolder extends RecyclerView.ViewHolder {
        private ItemSenderMediaTwoBinding senderMediaBinding;

        SenderMedia2ViewHolder(ItemSenderMediaTwoBinding senderMediaBinding) {
            super(senderMediaBinding.getRoot());
            this.senderMediaBinding = senderMediaBinding;
        }

        void bind(Message m) {
            senderMediaBinding.setMessage(m);
            senderMediaBinding.executePendingBindings();
        }
    }

}
