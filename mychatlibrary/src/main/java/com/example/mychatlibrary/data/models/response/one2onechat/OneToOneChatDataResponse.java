package com.example.mychatlibrary.data.models.response.one2onechat;

import com.example.mychatlibrary.data.models.response.messages.Message;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneToOneChatDataResponse {

    @SerializedName("user")
    @Expose
    private OneToOneUserResponse user;
    @SerializedName("chatrooms")
    @Expose
    private OneToOneChatRoomResponse chatrooms;
    @SerializedName("messages")
    @Expose
    private Message messages;
    @SerializedName("last_read_at")
    @Expose
    private String lastReadAt;

    public OneToOneUserResponse getUser() {
        return user;
    }

    public void setUser(OneToOneUserResponse user) {
        this.user = user;
    }

    public OneToOneChatRoomResponse getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(OneToOneChatRoomResponse chatrooms) {
        this.chatrooms = chatrooms;
    }

    public Message getMessages() {
        return messages;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }

    public String getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(String lastReadAt) {
        this.lastReadAt = lastReadAt;
    }
}
