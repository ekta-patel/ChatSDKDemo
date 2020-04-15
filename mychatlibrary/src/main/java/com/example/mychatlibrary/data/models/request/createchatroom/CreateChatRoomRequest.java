package com.example.mychatlibrary.data.models.request.createchatroom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateChatRoomRequest {

    @SerializedName("chatroom")
    @Expose
    private Chatroom chatroom;

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }
}
