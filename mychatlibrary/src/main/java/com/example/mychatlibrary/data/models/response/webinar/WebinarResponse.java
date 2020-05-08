package com.example.mychatlibrary.data.models.response.webinar;

import com.example.mychatlibrary.data.models.response.chatroom.Chatroom;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WebinarResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("chatrooms")
    @Expose
    private List<Chatroom> chatrooms = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Chatroom> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(List<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }
}
