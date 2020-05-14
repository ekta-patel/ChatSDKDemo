package com.example.mychatlibrary.data.models.response.base;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("success")
    private boolean status;
    @SerializedName("data")
    private T data;
    @SerializedName("error")
    private String error;
    private Throwable t;

    public BaseResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public BaseResponse(Throwable t) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
