package com.example.chatsdkimpldemo.ui.one2onechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemOnetooneChatBinding;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.mychatlibrary.data.models.response.one2onechat.OneToOneChatDataResponse;

import java.util.List;

public class OneToOneChatAdapter extends RecyclerView.Adapter<OneToOneChatAdapter.ViewHolder> {

    private List<OneToOneChatDataResponse> responseList;
    private RecyclerViewItemClickListener<OneToOneChatDataResponse> listener;

    OneToOneChatAdapter(List<OneToOneChatDataResponse> list, RecyclerViewItemClickListener<OneToOneChatDataResponse> listener) {
        this.responseList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOnetooneChatBinding oneToOneChatBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_onetoone_chat, parent, false);
        return new ViewHolder(oneToOneChatBinding, listener);
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

        private ItemOnetooneChatBinding chatBinding;
        private RecyclerViewItemClickListener<OneToOneChatDataResponse> listener;

        ViewHolder(ItemOnetooneChatBinding oneToOneChatBinding, RecyclerViewItemClickListener<OneToOneChatDataResponse> listener) {
            super(oneToOneChatBinding.getRoot());
            this.chatBinding = oneToOneChatBinding;
            this.listener = listener;
            View view = oneToOneChatBinding.getRoot();
            view.setOnClickListener(this);
        }

        void bind(OneToOneChatDataResponse chatResponse) {
            chatBinding.setOneToOne(chatResponse);
            chatBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, chatBinding.getOneToOne(), getAdapterPosition());
        }
    }
}
