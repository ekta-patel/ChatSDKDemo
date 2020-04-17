package com.example.mychatlibrary.data.models.response.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessagesResponseModel {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private MessageResponseDataModel data;
    //    @SerializedName("meta")
//    @Expose
//    private Meta meta;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

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

    public MessageResponseDataModel getData() {
        return data;
    }

    public void setData(MessageResponseDataModel data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
