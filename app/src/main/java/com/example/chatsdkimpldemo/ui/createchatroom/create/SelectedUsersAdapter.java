package com.example.chatsdkimpldemo.ui.createchatroom.create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemSelectedUsersCreateChatroomBinding;
import com.example.chatsdkimpldemo.ui.base.RecyclerViewItemClickListener;
import com.example.mychatlibrary.data.models.response.user.User;

import java.util.List;

public class SelectedUsersAdapter extends RecyclerView.Adapter<SelectedUsersAdapter.ViewHolder> {

    private List<User> userList;
    private RecyclerViewItemClickListener<User> listener;

    public SelectedUsersAdapter(List<User> userList, RecyclerViewItemClickListener<User> listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelectedUsersCreateChatroomBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_selected_users_create_chatroom, parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemSelectedUsersCreateChatroomBinding binding;
        private RecyclerViewItemClickListener<User> listener;

        ViewHolder(ItemSelectedUsersCreateChatroomBinding binding, RecyclerViewItemClickListener<User> listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            itemView.findViewById(R.id.ivClose).setOnClickListener(this);
        }

        void bind(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, binding.getUser(), getAdapterPosition());
        }
    }
}
