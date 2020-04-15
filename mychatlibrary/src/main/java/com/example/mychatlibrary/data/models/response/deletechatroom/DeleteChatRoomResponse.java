package com.example.mychatlibrary.data.models.response.deletechatroom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteChatRoomResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("html")
    @Expose
    private String html;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
