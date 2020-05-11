package com.example.mychatlibrary.data.models.request.createchatroom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateChatRoomRequest {

    @SerializedName("chatroom")
    @Expose
    private CreateChatRoomDataRequest chatRoomDataRequest;

    public CreateChatRoomDataRequest getChatRoomDataRequest() {
        return chatRoomDataRequest;
    }

    public void setChatRoomDataRequest(CreateChatRoomDataRequest chatRoomDataRequest) {
        this.chatRoomDataRequest = chatRoomDataRequest;
    }
}
