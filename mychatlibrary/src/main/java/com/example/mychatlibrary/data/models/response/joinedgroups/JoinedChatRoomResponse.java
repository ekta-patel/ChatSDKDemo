package com.example.mychatlibrary.data.models.response.joinedgroups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JoinedChatRoomResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("profilePic")
    @Expose
    private String profilePic = "https://www.elsetge.cat/myimg/f/49-495588_computer-icons-desktop-wallpaper-icon-group-inc-laurel.jpg";

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
