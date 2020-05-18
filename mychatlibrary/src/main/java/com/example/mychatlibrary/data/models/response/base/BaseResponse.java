package com.example.mychatlibrary.data.models.response.base;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("success")
    private boolean success;
    @SerializedName("data")
    private T data;
    @SerializedName("error")
    private String error;
    private Throwable t;
    private Status status;

    public BaseResponse(Status status, T data) {
        this.status = status;
        this.data = data;
        this.error = null;
    }

    public BaseResponse(Status status, Throwable t) {
        this.status = status;
        this.t = t;
        this.data = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Throwable getThrowable() {
        return t;
    }

    public void setThrowable(Throwable t) {
        this.t = t;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}


