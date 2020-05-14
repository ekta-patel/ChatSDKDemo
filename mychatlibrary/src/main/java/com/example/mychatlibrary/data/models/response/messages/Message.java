package com.example.mychatlibrary.data.models.response.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Parcelable {

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
    @SerializedName("mime_type")
    private String mimeType;
    @SerializedName("media_type")
    private String mediaType;

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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Message() {
    }

    protected Message(Parcel in) {
        id = in.readInt();
        body = in.readString();
        chatroomId = in.readInt();
        readAt = in.readString();
        deleteInSeconds = in.readInt();
        deletedAt = in.readString();
        userId = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        attachment = in.readString();
        mimeType = in.readString();
        mediaType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(body);
        dest.writeInt(chatroomId);
        dest.writeString(readAt);
        dest.writeInt(deleteInSeconds);
        dest.writeString(deletedAt);
        dest.writeInt(userId);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(attachment);
        dest.writeString(mimeType);
        dest.writeString(mediaType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
