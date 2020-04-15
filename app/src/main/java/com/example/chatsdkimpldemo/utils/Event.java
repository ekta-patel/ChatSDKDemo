package com.example.chatsdkimpldemo.utils;

public class Event<T> {

    private boolean isHandled = false;
    private T content;

    public Event(T response) {
        this.content = response;
    }

    public T getContentIfNotHandledOrReturnNull() {
        if (isHandled) {
            return null;
        } else {
            isHandled = true;
            return content;
        }
    }

    public T getContent() {
        return content;
    }
}
