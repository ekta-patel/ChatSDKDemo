package com.example.mychatlibrary.data.models.response.chatroom;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mychatlibrary.data.models.response.messages.Message;
import com.example.mychatlibrary.data.models.response.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Chatroom implements Parcelable {

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
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("messages")
    @Expose
    private Message messages;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessages() {
        return messages;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }

    protected Chatroom(Parcel in) {
        id = in.readInt();
        name = in.readString();
        directMessage = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            userIds = new ArrayList<Integer>();
            in.readList(userIds, Integer.class.getClassLoader());
        } else {
            userIds = null;
        }
        createdAt = in.readString();
        updatedAt = in.readString();
        lastMessage = in.readString();
        user = (User) in.readValue(User.class.getClassLoader());
        groupMember = in.readInt();
        lastReadAt = in.readString();
        message = (Message) in.readValue(Message.class.getClassLoader());
        messages = (Message) in.readValue(Message.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (directMessage ? 0x01 : 0x00));
        if (userIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(userIds);
        }
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(lastMessage);
        dest.writeValue(user);
        dest.writeInt(groupMember);
        dest.writeString(lastReadAt);
        dest.writeValue(message);
        dest.writeValue(messages);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Chatroom> CREATOR = new Parcelable.Creator<Chatroom>() {
        @Override
        public Chatroom createFromParcel(Parcel in) {
            return new Chatroom(in);
        }

        @Override
        public Chatroom[] newArray(int size) {
            return new Chatroom[size];
        }
    };
}
