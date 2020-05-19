package com.example.chatsdkimpldemo.ui.chatroomdetail;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemChatroomMediaBinding;
import com.example.chatsdkimpldemo.utils.Constants;
import com.example.mychatlibrary.data.models.response.messages.Message;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private List<Message> mediaList;

    MediaAdapter(List<Message> mediaList) {
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatroomMediaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chatroom_media, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mediaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemChatroomMediaBinding binding;

        ViewHolder(ItemChatroomMediaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Message message) {
            String mediaType = message.getMediaType();
            binding.setIsVideo(!TextUtils.isEmpty(mediaType) && mediaType.equalsIgnoreCase(Constants.MediaIdentifier.VIDEO.getValue()));
            binding.setMessage(message);
            binding.executePendingBindings();
        }
    }
}
