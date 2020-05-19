package com.example.mychatlibrary;

import android.content.Context;

import java.net.URISyntaxException;
import java.util.Map;

public class ChatSocketBuilder {
    private String baseUrl;
    private String channelName;
    private Map<String, String> headers;
    private Map<String, String> query;
    private ChatCallback callback;
    private Map<String, String> params;
    private Context context;

    public ChatSocketBuilder(Context context, String baseUrl, String channelName) {
        this.context = context;
        this.channelName = channelName;
        this.baseUrl = baseUrl;
    }

    public ChatSocketBuilder query(Map<String, String> options) {
        this.query = options;
        return this;
    }

    public ChatSocketBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public ChatSocketBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public ChatSocketBuilder addChatListener(ChatCallback callback) {
        this.callback = callback;
        return this;
    }

    public ChatCallback getCallback() {
        return callback;
    }

    String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public ConfigChatSocket build() throws URISyntaxException {
        synchronized (ChatSocketBuilder.this) {
            return new ConfigChatSocket(this, context);
        }
    }

    public interface ChatCallback {

        void onConnected();

        void onDisConnected();

        void onRejected();

        void onFailed(Throwable t);

        <T> void onReceived(T any);

    }

}
