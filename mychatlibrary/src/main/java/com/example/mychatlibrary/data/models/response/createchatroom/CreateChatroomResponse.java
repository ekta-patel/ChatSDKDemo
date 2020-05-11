package com.example.mychatlibrary.data.models.response.createchatroom;

import com.example.mychatlibrary.data.models.response.chatroom.ChatroomForMessages;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateChatroomResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("chatroom")
    @Expose
    private ChatroomForMessages chatroom;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChatroomForMessages getChatroom() {
        return chatroom;
    }

    public void setChatroom(ChatroomForMessages chatroom) {
        this.chatroom = chatroom;
    }
}
