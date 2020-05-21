package com.example.mychatlibrary.data.models.request.createchatroom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;

public class CreateChatRoomDataRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_ids")
    @Expose
    private List<Integer> userIds = null;
    @SerializedName("")
    @Expose
    private String description;
    @SerializedName("group_image")
    @Expose
    private File groupImage;
    private MediaType mediaType;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(File groupImage) {
        this.groupImage = groupImage;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
