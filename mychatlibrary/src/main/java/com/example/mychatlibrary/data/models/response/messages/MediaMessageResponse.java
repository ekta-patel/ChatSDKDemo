package com.example.mychatlibrary.data.models.response.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMessageResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private Message chatroom;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getChatroom() {
        return chatroom;
    }

    public void setChatroom(Message chatroom) {
        this.chatroom = chatroom;
    }
}
