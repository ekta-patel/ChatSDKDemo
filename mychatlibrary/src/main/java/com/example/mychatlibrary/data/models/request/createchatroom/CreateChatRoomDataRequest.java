package com.example.mychatlibrary.data.models.request.createchatroom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateChatRoomDataRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_ids")
    @Expose
    private List<Integer> userIds = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
