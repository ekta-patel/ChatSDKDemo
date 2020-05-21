package com.example.chatsdkimpldemo.ui.chatmain;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMediaTypeIvBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMediaTypeIvTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiveMessageTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiverMediaTypeAudioBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiverMediaTypeAudioTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiverMediaTypeDocBinding;
import com.example.chatsdkimpldemo.databinding.ItemReceiverMediaTypeDocTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeAudioBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeAudioTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeDocBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeDocTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeIvBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMediaTypeIvTwoBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageBinding;
import com.example.chatsdkimpldemo.databinding.ItemSenderMessageTwoBinding;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.io.IOException;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messageList;
    private RecyclerViewItemClickListener<Message> listener;
    private MediaPlayer player = null;

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
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE_IV1.getValue()) {
            ItemSenderMediaTypeIvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_iv, parent, false);
            return new SenderMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE_IV1.getValue()) {
            ItemReceiveMediaTypeIvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_media_type_iv, parent, false);
            return new ReceiverMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE_IV2.getValue()) {
            ItemSenderMediaTypeIvTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_iv_two, parent, false);
            return new SenderMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE_IV2.getValue()) {
            ItemReceiveMediaTypeIvTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receive_media_type_iv_two, parent, false);
            return new ReceiverMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_AUDIO1.getValue()) {
            ItemReceiverMediaTypeAudioBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receiver_media_type_audio, parent, false);
            return new ReceiverAudioMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_AUDIO2.getValue()) {
            ItemReceiverMediaTypeAudioTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receiver_media_type_audio_two, parent, false);
            return new ReceiverAudioMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_AUDIO1.getValue()) {
            ItemSenderMediaTypeAudioBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_audio, parent, false);
            return new SenderAudioMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_AUDIO2.getValue()) {
            ItemSenderMediaTypeAudioTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_audio_two, parent, false);
            return new SenderAudioMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE1.getValue()) {
            ItemSenderMediaTypeDocBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_doc, parent, false);
            return new SenderDocMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE2.getValue()) {
            ItemSenderMediaTypeDocTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sender_media_type_doc_two, parent, false);
            return new SenderDocMedia2ViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE1.getValue()) {
            ItemReceiverMediaTypeDocBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receiver_media_type_doc, parent, false);
            return new ReceiverDocMediaViewHolder(binding, listener);
        } else if (viewType == Constants.ViewHolderIdentifier.RECEIVER_MEDIA_DOCTYPE2.getValue()) {
            ItemReceiverMediaTypeDocTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_receiver_media_type_doc_two, parent, false);
            return new ReceiverDocMedia2ViewHolder(binding, listener);
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
        } else if (holder instanceof SenderAudioMediaViewHolder) {
            ((SenderAudioMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof SenderAudioMedia2ViewHolder) {
            ((SenderAudioMedia2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverAudioMediaViewHolder) {
            ((ReceiverAudioMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverAudioMedia2ViewHolder) {
            ((ReceiverAudioMedia2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverDocMediaViewHolder) {
            ((ReceiverDocMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof ReceiverDocMedia2ViewHolder) {
            ((ReceiverDocMedia2ViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof SenderDocMediaViewHolder) {
            ((SenderDocMediaViewHolder) holder).bind(messageList.get(position));
        } else if (holder instanceof SenderDocMedia2ViewHolder) {
            ((SenderDocMedia2ViewHolder) holder).bind(messageList.get(position));
        }
    }

    //3 is currentUserId
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
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE_IV2.getValue();
                            } else if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.AUDIO.getValue())) {
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_AUDIO2.getValue();
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
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE_IV1.getValue();
                            } else if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.AUDIO.getValue())) {
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_AUDIO1.getValue();
                            } else {
                                return Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE1.getValue();
                            }
                        } else {
                            return Constants.ViewHolderIdentifier.SENDER_MEDIA_DOCTYPE1.getValue();
                        }
                    }
                } else {
                    //Redundant
                    if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                        if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                            return Constants.ViewHolderIdentifier.SENDER_MEDIA_TYPE_IV1.getValue();
                        } else if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.AUDIO.getValue())) {
                            return Constants.ViewHolderIdentifier.SENDER_MEDIA_AUDIO1.getValue();
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
                    if (messageList.get(position - 1).getUserId() == messageList.get(position).getUserId()) {
                        return Constants.ViewHolderIdentifier.RECEIVER_TYPE2.getValue();
                    } else {
                        return Constants.ViewHolderIdentifier.RECEIVER_TYPE1.getValue();
                    }
                } else {
                    return Constants.ViewHolderIdentifier.RECEIVER_TYPE1.getValue();
                }
            } else {
                if (position != 0) {
                    if (messageList.get(position - 1).getUserId() == messageList.get(position).getUserId()) {
                        //Redundant if and its else remove when db is truncated
                        if (!TextUtils.isEmpty(messageList.get(position).getMediaType())) {
                            if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.IMAGE.getValue()) || messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue())) {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE_IV2.getValue();
                            } else if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.AUDIO.getValue())) {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_AUDIO2.getValue();
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
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE_IV1.getValue();
                            } else if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.AUDIO.getValue())) {
                                return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_AUDIO1.getValue();
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
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_TYPE_IV1.getValue();
                        } else if (messageList.get(position).getMediaType().equalsIgnoreCase(Constants.MediaIdentifier.AUDIO.getValue())) {
                            return Constants.ViewHolderIdentifier.RECEIVER_MEDIA_AUDIO1.getValue();
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

    class SenderAudioMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemSenderMediaTypeAudioBinding binding;
        private RecyclerViewItemClickListener<Message> listener;
        private boolean startPlaying;

        SenderAudioMediaViewHolder(ItemSenderMediaTypeAudioBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.playAudio).setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.playAudio) {
                onPlay(startPlaying, "http://13.235.232.157" + binding.getMessage().getAttachment());
                if (startPlaying) {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_pause_24dp));
                } else {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                }
                startPlaying = !startPlaying;
            }
            listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
        }

        private void onPlay(boolean start, String url) {
            if (start) {
                startPlaying(url);
            } else {
                stopPlaying();
            }
        }

        private void startPlaying(String url) {
            player = new MediaPlayer();
            try {
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(url);
                player.setOnCompletionListener(mp -> {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                    mp.stop();
                    mp.release();
                    if (player != null) {
                        player.release();
                        player = null;
                    }
                });
                player.prepare();
                int total = player.getDuration();
                binding.audioSeekbar.setMax(total);
                player.start();
            } catch (IOException | IllegalStateException e) {
                Log.e(TAG, "prepare() failed");
            }
        }

        private void stopPlaying() {
            if (player != null) {
                player.release();
                player = null;
            }
        }

    }

    class ReceiverAudioMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiverMediaTypeAudioBinding binding;
        private RecyclerViewItemClickListener<Message> listener;
        private boolean startPlaying;
        private int total;
        private Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    while (player != null) {
                        if (player.isPlaying()) {
                            int currentPosition = player.getCurrentPosition();
                            if (currentPosition < total) {
                                Thread.sleep(100);
                                currentPosition = player.getCurrentPosition();
                                binding.audioSeekbar.setProgress(currentPosition);
                            }
                        }

                    }
                } catch (Exception e) {
                    return;
                }
            }
        };


        ReceiverAudioMediaViewHolder(ItemReceiverMediaTypeAudioBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.playAudio).setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.playAudio) {
                onPlay(startPlaying, "http://13.235.232.157" + binding.getMessage().getAttachment());
                if (startPlaying) {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_pause_24dp));
                } else {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                }
                startPlaying = !startPlaying;
            } else {
                listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
            }
        }

        private void onPlay(boolean start, String url) {
            if (start) {
                startPlaying(url);
            } else {
                stopPlaying();
            }
        }

        private void startPlaying(String url) {
            player = new MediaPlayer();
            try {
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(url);
                player.setOnCompletionListener(mp -> {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                    mp.stop();
                    mp.release();
                    if (player != null) {
                        player.release();
                        player = null;
                    }

                });
                player.prepare();
                total = player.getDuration();
                binding.audioSeekbar.setMax(total);
                player.start();
                new Thread(runnable).start();
            } catch (IOException | IllegalStateException e) {
                Log.e(TAG, "prepare() failed");
            }
        }

        private void stopPlaying() {
            if (player != null) {
                player.release();
                player = null;
            }
        }
    }

    class ReceiverAudioMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiverMediaTypeAudioTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;
        private boolean startPlaying;
        private int total;
        private Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    while (player != null) {
                        if (player.isPlaying()) {
                            int currentPosition = player.getCurrentPosition();
                            if (currentPosition < total) {
                                Thread.sleep(100);
                                currentPosition = player.getCurrentPosition();
                                binding.audioSeekbar.setProgress(currentPosition);
                            }
                        }

                    }
                } catch (Exception e) {
                    return;
                }
            }
        };


        ReceiverAudioMedia2ViewHolder(ItemReceiverMediaTypeAudioTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.playAudio).setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.playAudio) {
                onPlay(startPlaying, "http://13.235.232.157" + binding.getMessage().getAttachment());
                if (startPlaying) {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_pause_24dp));
                } else {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                }
                startPlaying = !startPlaying;
            } else {
                listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
            }
        }

        private void onPlay(boolean start, String url) {
            if (start) {
                startPlaying(url);
            } else {
                stopPlaying();
            }
        }

        private void startPlaying(String url) {
            player = new MediaPlayer();
            try {
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(url);
                player.setOnCompletionListener(mp -> {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                    mp.stop();
                    mp.release();
                    if (player != null) {
                        player.release();
                        player = null;
                    }

                });
                player.prepare();
                total = player.getDuration();
                binding.audioSeekbar.setMax(total);
                player.start();
                new Thread(runnable).start();
            } catch (IOException | IllegalStateException e) {
                Log.e(TAG, "prepare() failed");
            }
        }

        private void stopPlaying() {
            if (player != null) {
                player.release();
                player = null;
            }
        }
    }

    class SenderAudioMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMediaTypeAudioTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;
        private boolean startPlaying;
        private int total;
        private Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    while (player != null) {
                        if (player.isPlaying()) {
                            int currentPosition = player.getCurrentPosition();
                            if (currentPosition < total) {
                                Thread.sleep(100);
                                currentPosition = player.getCurrentPosition();
                                binding.audioSeekbar.setProgress(currentPosition);
                            }
                        }

                    }
                } catch (Exception e) {
                    return;
                }
            }
        };

        SenderAudioMedia2ViewHolder(ItemSenderMediaTypeAudioTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.playAudio).setOnClickListener(this);
        }

        void bind(Message m) {
            binding.setMessage(m);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.playAudio) {
                onPlay(startPlaying, "http://13.235.232.157" + binding.getMessage().getAttachment());
                if (startPlaying) {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_pause_24dp));
                } else {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                }
                startPlaying = !startPlaying;
            } else {
                listener.onItemClick(v, binding.getMessage(), getAdapterPosition());
            }
        }

        private void onPlay(boolean start, String url) {
            if (start) {
                startPlaying(url);
            } else {
                stopPlaying();
            }
        }

        private void startPlaying(String url) {
            player = new MediaPlayer();
            try {
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(url);
                player.setOnCompletionListener(mp -> {
                    binding.playAudio.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_play_arrow_24dp));
                    mp.stop();
                    mp.release();
                    if (player != null) {
                        player.release();
                        player = null;
                    }

                });
                player.prepare();
                total = player.getDuration();
                binding.audioSeekbar.setMax(total);
                player.start();
                new Thread(runnable).start();
            } catch (IOException | IllegalStateException e) {
                Log.e(TAG, "prepare() failed");
            }
        }

        private void stopPlaying() {
            if (player != null) {
                player.release();
                player = null;
            }
        }

    }

    static class SenderDocMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemSenderMediaTypeDocBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderDocMediaViewHolder(ItemSenderMediaTypeDocBinding binding, RecyclerViewItemClickListener<Message> listener) {
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

    static class ReceiverDocMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiverMediaTypeDocBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverDocMediaViewHolder(ItemReceiverMediaTypeDocBinding binding, RecyclerViewItemClickListener<Message> listener) {
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

    static class ReceiverDocMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemReceiverMediaTypeDocTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        ReceiverDocMedia2ViewHolder(ItemReceiverMediaTypeDocTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
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

    static class SenderDocMedia2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSenderMediaTypeDocTwoBinding binding;
        private RecyclerViewItemClickListener<Message> listener;

        SenderDocMedia2ViewHolder(ItemSenderMediaTypeDocTwoBinding binding, RecyclerViewItemClickListener<Message> listener) {
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
