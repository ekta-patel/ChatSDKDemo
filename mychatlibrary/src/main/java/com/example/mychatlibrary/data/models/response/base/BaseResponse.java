package com.example.mychatlibrary.data.models.response.base;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private T data;
    private Throwable error;

    public BaseResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public BaseResponse(Throwable error) {
        this.error = error;
        this.data = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
