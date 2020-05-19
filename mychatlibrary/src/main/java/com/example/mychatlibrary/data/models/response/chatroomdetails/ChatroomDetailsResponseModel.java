package com.example.mychatlibrary.data.models.response.chatroomdetails;

import com.example.mychatlibrary.data.models.response.chatroom.ChatroomForDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatroomDetailsResponseModel {

    @SerializedName("chatroom")
    @Expose
    private ChatroomForDetails chatroom;

    public ChatroomForDetails getChatroom() {
        return chatroom;
    }

    public void setChatroom(ChatroomForDetails chatroom) {
        this.chatroom = chatroom;
    }
}
