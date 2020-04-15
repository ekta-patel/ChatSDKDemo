package com.example.mychatlibrary.data.models.response;

public class BaseResponse<T> {

    private T t;
    private Throwable throwable;

    public BaseResponse(T t) {
        this.t = t;
    }

    public BaseResponse(Throwable throwable) {
        this.throwable = throwable;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
