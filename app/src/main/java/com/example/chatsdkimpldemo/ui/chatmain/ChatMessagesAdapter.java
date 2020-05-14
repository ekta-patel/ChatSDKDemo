package com.example.chatsdkimpldemo.ui.chatmain;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMediaTypeIvBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMediaTypeIvTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiverMediaTypeOtherBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiverMediaTypeOtherTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeIvBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeIvTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeOtherBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeOtherTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageTwoBinding;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messageList;
    private RecyclerViewItemClickListener<Message> listener;

    ChatMessagesAdapter(List<Message> messageList, RecyclerViewItemClickListener<Message> listener) {
        this.messageList = messageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.ViewHolderIdentifier.SENDER_TYPE1.getValue()) {
            ItemSenderMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_message, parent, false);
            return new SenderViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_TYPE1.getValue()) {
            ItemReceiveMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_message, parent, false);
            return new ReceiverViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_TYPE2.getValue()) {
            ItemSenderMessageTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_message_two, parent, false);
            return new Sender2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_TYPE2.getValue()) {
            ItemReceiveMessageTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_message_two, parent, false);
            return new Receiver2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE1.getValue()) {
            ItemSenderMediaTypeIvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_iv, parent, false);
            return new SenderMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE1.getValue()) {
            ItemReceiveMediaTypeIvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_media_type_iv, parent, false);
            return new ReceiverMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE2.getValue()) {
            ItemSenderMediaTypeIvTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_iv_two, parent, false);
            return new SenderMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE2.getValue()) {
            ItemReceiveMediaTypeIvTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_media_type_iv_two, parent, false);
            return new ReceiverMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue()) {
            ItemReceiverMediaTypeOtherBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receiver_media_type_other, parent, false);
            return new ReceiverOtherMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE2.getValue()) {
            ItemReceiverMediaTypeOtherTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receiver_media_type_other_two, parent, false);
            return new ReceiverOtherMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE1.getValue()) {
            ItemSenderMediaTypeOtherBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_other, parent, false);
            return new SenderOtherMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE2.getValue()) {
            ItemSenderMediaTypeOtherTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_other_two, parent, false);
            return new SenderOtherMedia2ViewHolder(binding, listener);
        }
        //Redundant
        else {
            ItemReceiveMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_message, parent, false);
            return new ReceiverViewHolder(binding, listener);
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
        } else if (holder instanceof SenderOtherMediaViewHolder) {
            ((SenderOtherMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof SenderOtherMedia2ViewHolder) {
            ((SenderOtherMedia2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverOtherMediaViewHolder) {
            ((ReceiverOtherMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverOtherMedia2ViewHolder) {
            ((ReceiverOtherMedia2ViewHolder) holder).bind(messageList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message m = messageList.get(position);
        if (m.getUserId() == 3) {
            if (TextUtils.isEmpty(m.getAttachment())) {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() == 3) {
                        return Constants.ViewHolderIdentifier.SENDER_TYPE2.getValue();
                    } else {
                        return Constants.ViewHolderIdentifier.SENDER_TYPE1.getValue();
                    }
                } else {
                    return Constants.ViewHolderIdentifier.SENDER_TYPE1.getValue();
                }
            } else {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() == 3) {
                        //Redundant
                        if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                            if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE2.getValue();
                            } else {
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE2.getValue();
                            }
                        } else {
                            return Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE2.getValue();
                        }
                    } else {
                        //Redundant
                        if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                            if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE1.getValue();
                            } else {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue();
                            }
                        } else {
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue();
                        }
                    }
                } else {
                    //Redundant
                    if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                        if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                            return Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE1.getValue();
                        } else {
                            return Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE1.getValue();
                        }
                    } else {
                        return Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE1.getValue();
                    }
                }
            }
        } else {
            if (TextUtils.isEmpty(m.getAttachment())) {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() != 3) {
                        return Constants.ViewHolderIdentifier.RECEIVER_TYPE2.getValue();
                    } else {
                        return Constants.ViewHolderIdentifier.RECEIVER_TYPE1.getValue();
                    }
                } else {
                    return Constants.ViewHolderIdentifier.RECEIVER_TYPE1.getValue();
                }
            } else {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() == 3) {
                        //Redundant if and its else remove when db is truncated
                        if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                            if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE2.getValue();
                            } else {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE2.getValue();
                            }
                        } else {
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE2.getValue();
                        }
                    } else {
                        //Redundant
                        if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                            if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE1.getValue();
                            } else {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue();
                            }
                        } else {
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue();
                        }
                    }
                } else {
                    //Redundant
                    if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                        if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE1.getValue();
                        } else {
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue();
                        }
                    } else {
                        return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue();
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMessageBinding senderMessageBinding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderViewHolder(ItemSenderMessageBinding senderMessageBinding, RecyclerViewItemClickListener<Message> listener) {
            super(senderMessageBinding.getRoot());
            this.senderMessageBinding = senderMessageBinding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            senderMessageBinding.setMessage(m);
            senderMessageBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, senderMessageBinding.getMessage(), getAdapterPosition());
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiveMessageBinding receiveMessageBinding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverViewHolder(ItemReceiveMessageBinding receiveMessageBinding, RecyclerViewItemClickListener<Message> listener) {
            super(receiveMessageBinding.getRoot());
            this.receiveMessageBinding = receiveMessageBinding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            receiveMessageBinding.setMessage(m);
            receiveMessageBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, receiveMessageBinding.getMessage(), getAdapterPosition());
        }
    }

    static class Receiver2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiveMessageTwoBinding receiveMessageBinding;
        private RecyclerViewItemClickListener<Message> listener;

        Receiver2ViewHolder(ItemReceiveMessageTwoBinding receiveMessageBinding, RecyclerViewItemClickListener<Message> listener) {
            super(receiveMessageBinding.getRoot());
            this.receiveMessageBinding = receiveMessageBinding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            receiveMessageBinding.setMessage(m);
            receiveMessageBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, receiveMessageBinding.getMessage(), getAdapterPosition());
        }
    }

    static class Sender2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMessageTwoBinding senderMessageBinding;
        private RecyclerViewItemClickListener<Message> listener;

        Sender2ViewHolder(ItemSenderMessageTwoBinding senderMessageBinding, RecyclerViewItemClickListener<Message> listener) {
            super(senderMessageBinding.getRoot());
            this.senderMessageBinding = senderMessageBinding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            senderMessageBinding.setMessage(m);
            senderMessageBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, senderMessageBinding.getMessage(), getAdapterPosition());
        }
    }

    static class SenderMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMediaTypeIvBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderMediaViewHolder(ItemSenderMediaTypeIvBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setIsVideo(m.getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue()));
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class ReceiverMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiveMediaTypeIvBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverMediaViewHolder(ItemReceiveMediaTypeIvBinding receiveMediaBinding, RecyclerViewItemClickListener<Message> listener) {
            super(receiveMediaBinding.getRoot());
            this.binding = receiveMediaBinding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setIsVideo(m.getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue()));
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class ReceiverMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiveMediaTypeIvTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverMedia2ViewHolder(ItemReceiveMediaTypeIvTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setIsVideo(m.getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue()));
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class SenderMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMediaTypeIvTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderMedia2ViewHolder(ItemSenderMediaTypeIvTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setIsVideo(m.getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue()));
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class SenderOtherMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemSenderMediaTypeOtherBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderOtherMediaViewHolder(ItemSenderMediaTypeOtherBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class ReceiverOtherMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiverMediaTypeOtherBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverOtherMediaViewHolder(ItemReceiverMediaTypeOtherBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class ReceiverOtherMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiverMediaTypeOtherTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverOtherMedia2ViewHolder(ItemReceiverMediaTypeOtherTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

    static class SenderOtherMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMediaTypeOtherTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderOtherMedia2ViewHolder(ItemSenderMediaTypeOtherTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }
    }

}
