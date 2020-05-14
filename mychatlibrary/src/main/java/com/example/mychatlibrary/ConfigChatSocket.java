package com.example.mychatlibrary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.mychatlibrary.actioncables.ActionCable;
import com.example.mychatlibrary.actioncables.Channel;
import com.example.mychatlibrary.actioncables.Consumer;
import com.example.mychatlibrary.actioncables.Subscription;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.messages.MediaMessageResponse;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.myclass.MyClassResponse;
import com.example.mychatlibrary.data.models.response.webinar.WebinarResponse;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

public final class ConfigChatSocket {

    private Consumer _Consumer;
    private static volatile ConfigChatSocket INSTANCE;
    private Builder builder;
    private Subscription subscriptionChat;
    private ChatApiHelper apiHelper;

    private ConfigChatSocket(Builder builder) throws URISyntaxException {
        this.builder = builder;
        this.apiHelper = ChatApiHelper.getInstance();
        initConfig(builder.baseUrl, builder.query, builder.headers);
    }

    private void initConfig(String baseUrl, Map<String, String> query, Map<String, String> headers) throws URISyntaxException {
        URI uri = new URI(baseUrl);
        Consumer.Options consumerOptions = new Consumer.Options();
        if (query != null) {
            consumerOptions.getConnection().setQuery(query);
        }
        if (headers != null) {
            consumerOptions.getConnection().setHeaders(headers);
        }
        _Consumer = ActionCable.INSTANCE.createConsumer(uri, consumerOptions);
    }

    private void initSubscription(String channelName, Map<String, ?> params, final ChatCallback callback) {
        Channel bookingIdChannel;
        if (params != null) {
            bookingIdChannel = new Channel(channelName, params);
        } else {
            bookingIdChannel = new Channel(channelName, new HashMap<>());
        }
        subscriptionChat = _Consumer.getSubscriptions().create(bookingIdChannel);

        if (callback != null) {
            subscriptionChat.setOnConnected(() -> {
                callback.onConnected();
                return null;
            });

            subscriptionChat.setOnDisconnected(() -> {
                callback.onDisConnected();
                return null;
            });

            subscriptionChat.setOnFailed(throwable -> {
                callback.onFailed(throwable);
                return null;
            });

            subscriptionChat.setOnRejected(() -> {
                callback.onRejected();
                return null;
            });

            subscriptionChat.setOnReceived(o -> {
                callback.onReceived(o);
                return null;
            });
        }
        _Consumer.connect();
    }

    public void sendMessage(int channelId, String body) {
        Map<String, Object> map = new HashMap<>();
        map.put("body", body);
        map.put("channelId", channelId);
        subscriptionChat.perform("speak", map);
    }

    public void sendMediaMessage(int channelId, String body, File f, String mimeType, String extension, String fileName) {
        Map<String, Object> map = new HashMap<>();
        map.put("body", body);
        map.put("channelId", channelId);
        map.put("file", f);
        map.put("mime_type", mimeType);
        map.put("file_extension", extension);
        map.put("file_name", fileName);
        subscriptionChat.perform("speak", map);
    }

    public LiveData<BaseResponse<MessagesResponseModel>> getChatRoomMessages(int chatRoomId) {
        MediatorLiveData<BaseResponse<MessagesResponseModel>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getChatRoomMessages(chatRoomId), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<MessagesResponseModel>> getUserMessages(int userId) {
        MediatorLiveData<BaseResponse<MessagesResponseModel>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getUserMessages(userId), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<DeleteChatRoomResponse>> deleteChatroom(int chatRoomId) {
        MediatorLiveData<BaseResponse<DeleteChatRoomResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.deleteChatRoom(chatRoomId), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<LeaveChatroomResponse>> leaveChatroom(int chatRoomId) {
        MediatorLiveData<BaseResponse<LeaveChatroomResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.leaveChatRoom(chatRoomId), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<MyClassResponse>> getOneToOneChatRooms() {
        MediatorLiveData<BaseResponse<MyClassResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getOneToOneChatRooms(), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<List<GroupChatResponse>>> getGroupChatRooms() {
        MediatorLiveData<BaseResponse<List<GroupChatResponse>>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getGroupChatRooms(), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<CreateChatroomResponse>> createChatRoom(CreateChatRoomRequest request) {
        MediatorLiveData<BaseResponse<CreateChatroomResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.createChatRoom(request), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<JoinChatRoomResponse>> joinChatRoom(int chatroomId) {
        MediatorLiveData<BaseResponse<JoinChatRoomResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.joinChatRoom(chatroomId), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<WebinarResponse>> getJoinedChatRooms() {
        MediatorLiveData<BaseResponse<WebinarResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getJoinedChatRooms(), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<MediaMessageResponse>> sendMediaMessage(int chatroomId, File f, MediaType mediaType) {
        MediatorLiveData<BaseResponse<MediaMessageResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.sendMediaMessage(chatroomId, f, mediaType), data::postValue);
        return data;
    }

    public void connect(Map<String, String> v) {
        initSubscription(builder.channelName, v, builder.callback);
    }

    public void disconnect() {
        _Consumer.disconnect();
    }

    public static class Builder {
        private String baseUrl;
        private String channelName;
        private Map<String, String> headers;
        private Map<String, String> query;
        private ChatCallback callback;
        private Map<String, String> params;

        public Builder(String baseUrl, String channelName) {
            this.channelName = channelName;
            this.baseUrl = baseUrl;
        }

        public Builder query(Map<String, String> options) {
            this.query = options;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addChatListener(ChatCallback callback) {
            this.callback = callback;
            return this;
        }

        public ConfigChatSocket build() throws URISyntaxException {
            synchronized (Builder.this) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigChatSocket(this);
                }
                return INSTANCE;
            }
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
