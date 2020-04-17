package com.example.mychatlibrary.data.models.response.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponseDataModel {

    @SerializedName("chatroom")
    @Expose
    private MessageChatRoom messageChatroom;

    public MessageChatRoom getMessageChatroom() {
        return messageChatroom;
    }

    public void setMessageChatroom(MessageChatRoom messageChatroom) {
        this.messageChatroom = messageChatroom;
    }
}

