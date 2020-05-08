package com.example.mychatlibrary;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mychatlibrary.actioncables.ActionCable;
import com.example.mychatlibrary.actioncables.Channel;
import com.example.mychatlibrary.actioncables.Consumer;
import com.example.mychatlibrary.actioncables.Subscription;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.webinar.WebinarResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.myclass.MyClassResponse;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigChatSocket {

    private Consumer _Consumer;
    private static volatile ConfigChatSocket INSTANCE;
    private Builder builder;
    private Subscription subscriptionChat;
    private ChatApiHelper apiHelper;

    private ConfigChatSocket(Builder builder) throws URISyntaxException {
        this.builder = builder;
        this.apiHelper = ChatApiHelper.getInstance();
        initConfig(builder.baseUrl, builder.options, builder.headers);
    }

    private void initConfig(String baseUrl, Map<String, String> options, Map<String, String> headers) throws URISyntaxException {
        URI uri = new URI(baseUrl);
        Consumer.Options consumerOptions = new Consumer.Options();
        if (options != null) {
            consumerOptions.getConnection().setQuery(options);
        }
        if (headers != null) {
            consumerOptions.getConnection().setHeaders(headers);
        }
        _Consumer = ActionCable.INSTANCE.createConsumer(uri, consumerOptions);
    }

    private void initSubscription(String channelName, Map<String, ?> options, final ChatCallback callback) {
        Channel bookingIdChannel = new Channel(channelName, options);
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

    public void sendMessage(int channelId, String body, boolean hasMedia, File f) {
        Map<String, Object> map = new HashMap<>();
        map.put("body", body);
        map.put("channelId", channelId);
        if (!hasMedia) {
            subscriptionChat.perform("speak", map);
        } else {
//            subscriptionChat.perform(
//                    "speak", mapOf(
//                            "body"to"send msg from user 2 to chatroom:1",
//                            "channelId"to 1,
//                            "mime_type"to mimeType,
//                            "file_extension"to extention,
//                            "file_name"to logFileName.replace(extention, ""),
//                            "file"to imageBytes
//                    )
//            );
        }
    }

    public MutableLiveData<MessagesResponseModel> getChatRoomMessages(int chatRoomId) {
        MediatorLiveData<MessagesResponseModel> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getChatRoomMessages(chatRoomId), data::postValue);
        return data;
    }

    public MutableLiveData<MessagesResponseModel> getUserMessages(int userId) {
        MediatorLiveData<MessagesResponseModel> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getUserMessages(userId), data::postValue);
        return data;
    }

    public MutableLiveData<DeleteChatRoomResponse> deleteChatroom(int chatRoomId) {
        MediatorLiveData<DeleteChatRoomResponse> data = new MediatorLiveData<>();
        data.addSource(apiHelper.deleteChatRoom(chatRoomId), data::postValue);
        return data;
    }

    public MutableLiveData<LeaveChatroomResponse> leaveChatroom(int chatRoomId) {
        MediatorLiveData<LeaveChatroomResponse> data = new MediatorLiveData<>();
        data.addSource(apiHelper.leaveChatRoom(chatRoomId), data::postValue);
        return data;
    }

    public MutableLiveData<MyClassResponse> getOneToOneChatRooms() {
        MediatorLiveData<MyClassResponse> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getOneToOneChatRooms(), data::postValue);
        return data;
    }

    public MutableLiveData<List<GroupChatResponse>> getGroupChatRooms() {
        MediatorLiveData<List<GroupChatResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getGroupChatRooms(), data::postValue);
        return data;
    }

    public MutableLiveData<CreateChatroomResponse> createChatRoom(CreateChatRoomRequest request) {
        MediatorLiveData<CreateChatroomResponse> data = new MediatorLiveData<>();
        data.addSource(apiHelper.createChatRoom(request), data::postValue);
        return data;
    }

    public MutableLiveData<JoinChatRoomResponse> joinChatRoom(int chatroomId) {
        MediatorLiveData<JoinChatRoomResponse> data = new MediatorLiveData<>();
        data.addSource(apiHelper.joinChatRoom(chatroomId), data::postValue);
        return data;
    }

    public MutableLiveData<WebinarResponse> getJoinedChatRooms() {
        MediatorLiveData<WebinarResponse> data = new MediatorLiveData<>();
        data.addSource(apiHelper.getJoinedChatRooms(), data::postValue);
        return data;
    }

    public void connect() {
        initSubscription(builder.channelName, builder.options, builder.callback);
    }

    public void disconnect() {
        _Consumer.disconnect();
    }

    public static class Builder {
        private String baseUrl;
        private String channelName;
        private Map<String, String> headers;
        private Map<String, String> options;
        private ChatCallback callback;

        public Builder(String baseUrl, String channelName) {
            this.channelName = channelName;
            this.baseUrl = baseUrl;
        }

        public Builder query(Map<String, String> options) {
            this.options = options;
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
