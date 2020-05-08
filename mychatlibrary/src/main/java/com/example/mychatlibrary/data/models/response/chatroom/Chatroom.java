package com.example.mychatlibrary.data.models.response.chatroom;

import com.example.mychatlibrary.data.models.response.messages.Message;
import com.example.mychatlibrary.data.models.response.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chatroom {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("direct_message")
    @Expose
    private boolean directMessage;
    @SerializedName("user_ids")
    @Expose
    private List<Integer> userIds = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("last_message")
    @Expose
    private String lastMessage;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("group_member")
    @Expose
    private int groupMember;
    @SerializedName("last_read_at")
    @Expose
    private String lastReadAt;
    @SerializedName("messages")
    @Expose
    private Message message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirectMessage() {
        return directMessage;
    }

    public void setDirectMessage(boolean directMessage) {
        this.directMessage = directMessage;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(int groupMember) {
        this.groupMember = groupMember;
    }

    public String getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(String lastReadAt) {
        this.lastReadAt = lastReadAt;
    }

    public Message getMessages() {
        return message;
    }

    public void setMessages(Message message) {
        this.message = message;
    }
}
