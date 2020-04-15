package com.example.mychatlibrary.data.models.response.one2onechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneToOneChatResponse {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<OneToOneChatDataResponse> data;
    @SerializedName("meta")
    @Expose
    private OneToOneMetaResponse meta;
    @SerializedName("error")
    @Expose
    private List<String> error = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OneToOneChatDataResponse> getData() {
        return data;
    }

    public void setData(List<OneToOneChatDataResponse> data) {
        this.data = data;
    }

    public OneToOneMetaResponse getMeta() {
        return meta;
    }

    public void setMeta(OneToOneMetaResponse meta) {
        this.meta = meta;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}
