package com.example.chatsdkimpldemo.ui.shareselection;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsdkimpldemo.R;
import com.example.chatsdkimpldemo.databinding.ItemCreateChatUserBinding;
import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;

import java.util.List;

public class SelectUsersForShareAdapter extends RecyclerView.Adapter<SelectUsersForShareAdapter.ViewHolder> {

    private SelectionTracker<Chatroom> selectionTracker;

    private List<Chatroom> responseList;

    SelectUsersForShareAdapter(List<Chatroom> list) {
        this.responseList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCreateChatUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_create_chat_user, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chatroom c = responseList.get(position);
        holder.bind(c, selectionTracker.isSelected(c));
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void setSelectionTracker(
            SelectionTracker<Chatroom> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    static class Predicate extends SelectionTracker.SelectionPredicate<Chatroom> {

        @Override
        public boolean canSetStateForKey(@NonNull Chatroom key, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSetStateAtPosition(int position, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSelectMultiple() {
            return false;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCreateChatUserBinding binding;

        public ViewHolder(ItemCreateChatUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Chatroom c, boolean isSelected) {
            binding.setChatroom(c);
            binding.executePendingBindings();
            if (selectionTracker != null) {
                if (isSelected) {
                    binding.getRoot().findViewById(R.id.isSelected).setVisibility(View.VISIBLE);
                    binding.getRoot().setActivated(true);
                } else {
                    binding.getRoot().findViewById(R.id.isSelected).setVisibility(View.GONE);
                    binding.getRoot().setActivated(false);
                }
            }
        }

        Details getItemDetails() {
            return new Details(getAdapterPosition(), binding.getChatroom());
        }
    }

    static class KeyProvider extends ItemKeyProvider<Chatroom> {

        private List<Chatroom> chatrooms;

        KeyProvider(List<Chatroom> chatrooms) {
            super(ItemKeyProvider.SCOPE_MAPPED);
            this.chatrooms = chatrooms;
        }

        @Nullable
        @Override
        public Chatroom getKey(int position) {
            return chatrooms.get(position);
        }

        @Override
        public int getPosition(@NonNull Chatroom key) {
            return chatrooms.indexOf(key);
        }
    }

    static class DetailsLookup extends ItemDetailsLookup<Chatroom> {

        private RecyclerView recyclerView;

        DetailsLookup(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<Chatroom> getItemDetails(@NonNull MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof ViewHolder) {
                    return ((ViewHolder) viewHolder).getItemDetails();
                }
            }
            return null;
        }
    }

    static class Details extends ItemDetailsLookup.ItemDetails<Chatroom> {

        private long position;
        private Chatroom chatroom;

        public Details(long position, Chatroom chatroom) {
            this.position = position;
            this.chatroom = chatroom;
        }

        @Override
        public int getPosition() {
            return (int) position;
        }

        @Nullable
        @Override
        public Chatroom getSelectionKey() {
            return chatroom;
        }

        @Override
        public boolean inSelectionHotspot(@NonNull MotionEvent e) {
            return true;
        }
    }
}
