package com.example.mychatlibrary.utils;

import java.io.IOException;

public class NoInternetException extends IOException {

    public NoInternetException(String message) {
        super(message);
    }
}
