package com.example.mychatlibrary.data.models.response.base;

public class Resource<T> {

    private T data;
    private String error;

    private Resource(T data) {
        this.data = data;
    }

    private Resource(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public static class SUCCESS<T> extends Resource<T> {
        public SUCCESS(T data) {
            super(data);
        }
    }

    public static class FAILURE<T> extends Resource<T> {
        public FAILURE(T data, String error) {
            super(data, error);
        }
    }

    public static class LOADING<T> extends Resource<T> {
        public LOADING(T data) {
            super(data);
        }
    }

}