package com.example.mychatlibrary.data.models.response.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("chatroom_id")
    @Expose
    private int chatroomId;
    @SerializedName("read_at")
    @Expose
    private String readAt;
    @SerializedName("delete_in_seconds")
    @Expose
    private int deleteInSeconds;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("attachment")
    @Expose
    private String attachment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

    public int getDeleteInSeconds() {
        return deleteInSeconds;
    }

    public void setDeleteInSeconds(int deleteInSeconds) {
        this.deleteInSeconds = deleteInSeconds;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
