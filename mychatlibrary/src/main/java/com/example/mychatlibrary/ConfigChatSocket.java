package com.example.mychatlibrary;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mychatlibrary.actioncables.ActionCable;
import com.example.mychatlibrary.actioncables.Channel;
import com.example.mychatlibrary.actioncables.Consumer;
import com.example.mychatlibrary.actioncables.Subscription;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.chatroomdetails.ChatroomDetailsResponseModel;
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
    private volatile ConfigChatSocket INSTANCE;
    private ChatSocketBuilder builder;
    private Context context;
    private Subscription subscriptionChat;
    private ChatApiHelper apiHelper;

    ConfigChatSocket(ChatSocketBuilder builder, Context context) throws URISyntaxException {
        this.builder = builder;
        this.apiHelper = ChatApiHelper.getInstance(context);
        initConfig(builder.getBaseUrl(), builder.getQuery(), builder.getHeaders());
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

    private void initSubscription(String channelName, Map<String, ?> params, final ChatSocketBuilder.ChatCallback callback) {
        Channel bookingIdChannel;
        if (params != null) {
            bookingIdChannel = new Channel(channelName, params);
        } else {
            bookingIdChannel = new Channel(channelName, new HashMap<>());
            _Consumer.connect();
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
        return apiHelper.getChatRoomMessages(chatRoomId);
    }

    public LiveData<BaseResponse<MessagesResponseModel>> getUserMessages(int userId) {
        return apiHelper.getUserMessages(userId);
    }

    public LiveData<BaseResponse<DeleteChatRoomResponse>> deleteChatroom(int chatRoomId) {
        return apiHelper.deleteChatRoom(chatRoomId);
    }

    public LiveData<BaseResponse<LeaveChatroomResponse>> leaveChatroom(int chatRoomId) {
        return apiHelper.leaveChatRoom(chatRoomId);
    }

    public LiveData<BaseResponse<MyClassResponse>> getOneToOneChatRooms() {
        return apiHelper.getOneToOneChatRooms();
    }

    public LiveData<BaseResponse<List<GroupChatResponse>>> getGroupChatRooms() {
        return apiHelper.getGroupChatRooms();
    }

    public LiveData<BaseResponse<CreateChatroomResponse>> createChatRoom(CreateChatRoomRequest request) {
        return apiHelper.createChatRoom(request);
    }

    public LiveData<BaseResponse<JoinChatRoomResponse>> joinChatRoom(int chatroomId) {
        MediatorLiveData<BaseResponse<JoinChatRoomResponse>> data = new MediatorLiveData<>();
        data.addSource(apiHelper.joinChatRoom(chatroomId), data::postValue);
        return data;
    }

    public LiveData<BaseResponse<WebinarResponse>> getJoinedChatRooms() {
        return apiHelper.getJoinedChatRooms();
    }

    public LiveData<BaseResponse<MediaMessageResponse>> sendMediaMessage(int chatroomId, File f, MediaType mediaType) {
        return apiHelper.sendMediaMessage(chatroomId, f, mediaType);
    }

    public LiveData<BaseResponse<ChatroomDetailsResponseModel>> getChatroomDetails(int chatroomId) {
        return apiHelper.getChatroomDetails(chatroomId);
    }

    public void connect(Map<String, String> v) {
        initSubscription(builder.getChannelName(), v, builder.getCallback());
    }

    public void disconnect() {
        _Consumer.disconnect();
    }

}
