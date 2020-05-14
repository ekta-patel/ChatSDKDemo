package com.example.chatsdkimpldemo.ui.activities;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatlibrary.ConfigChatSocket;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.messages.MediaMessageResponse;
import com.example.mychatlibrary.data.models.response.messages.Message;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.myclass.MyClassResponse;
import com.example.mychatlibrary.data.models.response.webinar.WebinarResponse;
import com.example.mychatlibrary.utils.MD5;

import java.io.File;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private ConfigChatSocket chatSocket;
    private ConfigChatSocket.ChatCallback defaultChannelListener = new ConfigChatSocket.ChatCallback() {

        @Override
        public void onConnected() {
            Log.e(TAG, "CONNECTED");
        }

        @Override
        public void onDisConnected() {
            Log.e(TAG, "DIS-CONNECTED");
        }

        @Override
        public void onRejected() {
            Log.e(TAG, "REJECTED");
        }

        @Override
        public void onFailed(Throwable t) {
            Log.e(TAG, "FAILED" + t.getMessage());
        }

        @Override
        public <T> void onReceived(T any) {
            Log.e(TAG, "RECEIVED" + any.toString());
            if (any instanceof Map) {
                Map response = (Map) any;
                if (response.containsKey("event")) {
                    if (Objects.requireNonNull(response.get("event")).equals("created")) {
                        if (response.containsKey("data")) {
                            Map message = (Map) response.get("data");
                            if (message != null) {
                                Message message1 = new Message();
                                int messageId = message.containsKey("id") ? Objects.requireNonNull((Double) message.get("id")).intValue() : -1;
                                message1.setId(messageId);
                                String messageBody = message.containsKey("body") ? (String) message.get("body") : "";
                                message1.setBody(messageBody);
                                int messageChatroomId = message.containsKey("chatroom_id") ? ((Double) Objects.requireNonNull(message.get("chatroom_id"))).intValue() : -1;
                                message1.setChatroomId(messageChatroomId);
                                String readAt = message.containsKey("read_at") ? ((String) message.get("read_at")) : "";
                                message1.setReadAt(readAt);
                                int deleteInSeconds = message.containsKey("delete_in_seconds") ? ((Double) Objects.requireNonNull(message.get("delete_in_seconds"))).intValue() : -1;
                                message1.setDeleteInSeconds(deleteInSeconds);
                                String deletedAt = message.containsKey("deleted_at") ? ((String) message.get("deleted_at")) : "";
                                message1.setDeletedAt(deletedAt);
                                int messageUserId = message.containsKey("user_id") ? Objects.requireNonNull(((Double) message.get("user_id"))).intValue() : -1;
                                message1.setUserId(messageUserId);
                                String messageCreatedAt = message.containsKey("created_at") ? (String) message.get("created_at") : "";
                                message1.setCreatedAt(messageCreatedAt);
                                String messageUpdatedAt = message.containsKey("updated_at") ? (String) message.get("updated_at") : "";
                                message1.setUpdatedAt(messageUpdatedAt);
                                String attachment = message.containsKey("attachment") ? (String) message.get("attachment") : "";
                                message1.setAttachment(attachment);
                                String mimeType = message.containsKey("mime_type") ? (String) message.get("mime_type") : "";
                                message1.setMimeType(mimeType);
                                String mediaType = message.containsKey("media_type") ? (String) message.get("media_type") : "";
                                message1.setMediaType(mediaType);
                                messageMutableLiveData.postValue(message1);
                            }
                        }
                    } else {

                    }
                }
            }
        }
    };

    private ConfigChatSocket.ChatCallback newChannelListener = new ConfigChatSocket.ChatCallback() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisConnected() {

        }

        @Override
        public void onRejected() {

        }

        @Override
        public void onFailed(Throwable t) {

        }

        @Override
        public <T> void onReceived(T any) {

        }
    };


    public void createDefaultChatSocket(Map<String, String> params) {
        try {
            Map<String, String> options = new HashMap<>();
            try {
                options.put("login", "useremail@gmail.com");
                options.put("password", MD5.getMd5("Tatva@123"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            chatSocket = new ConfigChatSocket.Builder("ws://13.235.232.157/cable", "ChatroomsChannel").params(params).query(options).addChatListener(defaultChannelListener).build();
        } catch (
                URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private MutableLiveData<Message> messageMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private MediatorLiveData<DeleteChatRoomResponse> deleteChatRoomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<LeaveChatroomResponse> leaveChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<MessagesResponseModel> messagesResponseModelMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<MyClassResponse> getOneToOneChatRoomsMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<GroupChatResponse>> getGroupChatRoomsMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<CreateChatroomResponse> createChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<WebinarResponse> joinedChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<JoinChatRoomResponse> joinChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<MediaMessageResponse> mediaMessageResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Throwable> error = new MediatorLiveData<>();


    public ConfigChatSocket getChatSocket() {
        return chatSocket;
    }

    public void connectWebSocket(Map<String, String> v) {
        chatSocket.connect(v);
    }

    public void disconnectWebSocket() {
        chatSocket.disconnect();
    }

    public void deleteChatroom(int chatRoomId) {
        _isLoading.postValue(true);
        deleteChatRoomResponseMediatorLiveData.addSource(chatSocket.deleteChatroom(chatRoomId), response -> {
            if (response.getThrowable() == null) {
                deleteChatRoomResponseMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void leaveChatroom(int chatRoomId) {
        _isLoading.postValue(true);
        leaveChatroomResponseMediatorLiveData.addSource(chatSocket.leaveChatroom(chatRoomId), response -> {
            if (response.getThrowable() == null) {
                leaveChatroomResponseMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void getChatRoomMessages(int chatRoomId) {
        _isLoading.postValue(true);
        messagesResponseModelMediatorLiveData.addSource(chatSocket.getChatRoomMessages(chatRoomId), response -> {
            if (response.getThrowable() == null) {
                messagesResponseModelMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void getUserMessages(int userId) {
        _isLoading.postValue(true);
        messagesResponseModelMediatorLiveData.addSource(chatSocket.getUserMessages(userId), response -> {
            if (response.getThrowable() == null) {
                messagesResponseModelMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void getJoinedChatRooms() {
        _isLoading.postValue(true);
        joinedChatroomResponseMediatorLiveData.addSource(chatSocket.getJoinedChatRooms(), response -> {
            if (response.getThrowable() == null) {
                joinedChatroomResponseMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void getChatRooms() {
        _isLoading.postValue(true);
        getGroupChatRoomsMediatorLiveData.addSource(chatSocket.getGroupChatRooms(), response -> {
            if (response.getThrowable() == null) {
                getGroupChatRoomsMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void createChatRoom(CreateChatRoomRequest request) {
        _isLoading.postValue(true);
        createChatroomResponseMediatorLiveData.addSource(chatSocket.createChatRoom(request), response -> {
            if (response.getThrowable() == null) {
                createChatroomResponseMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void joinChatRoom(int chatroomId) {
        _isLoading.postValue(true);
        joinChatroomResponseMediatorLiveData.addSource(chatSocket.joinChatRoom(chatroomId), response -> {
            if (response.getThrowable() == null) {
                joinChatroomResponseMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void getOneToOneChatRooms() {
        _isLoading.postValue(true);
        getOneToOneChatRoomsMediatorLiveData.addSource(chatSocket.getOneToOneChatRooms(), response -> {
            if (response.getThrowable() == null) {
                getOneToOneChatRoomsMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public void sendMediaMessage(int chatroomId, File f, MediaType mediaType) {
        _isLoading.postValue(true);
        mediaMessageResponseMediatorLiveData.addSource(chatSocket.sendMediaMessage(chatroomId, f, mediaType), response -> {
            if (response.getThrowable() == null) {
                mediaMessageResponseMediatorLiveData.postValue(response.getData());
            } else {
                error.postValue(response.getThrowable());
            }
            _isLoading.postValue(false);
        });
    }

    public LiveData<MyClassResponse> getOneToOneChatResponseLiveData() {
        return getOneToOneChatRoomsMediatorLiveData;
    }

    public LiveData<List<GroupChatResponse>> getGroupChatResponseLiveData() {
        return getGroupChatRoomsMediatorLiveData;
    }

    public LiveData<CreateChatroomResponse> getCreateChatRoomResponseLiveData() {
        return createChatroomResponseMediatorLiveData;
    }

    public LiveData<WebinarResponse> getJoinedChatRoomResponseLiveData() {
        return joinedChatroomResponseMediatorLiveData;
    }

    public LiveData<JoinChatRoomResponse> getJoinChatRoomResponseLiveData() {
        return joinChatroomResponseMediatorLiveData;
    }

    public LiveData<DeleteChatRoomResponse> getDeleteChatroomLiveData() {
        return deleteChatRoomResponseMediatorLiveData;
    }

    public LiveData<LeaveChatroomResponse> getLeaveChatroomLiveData() {
        return leaveChatroomResponseMediatorLiveData;
    }

    public LiveData<MessagesResponseModel> getMessageResponseLiveData() {
        return messagesResponseModelMediatorLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public LiveData<Message> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }

    public LiveData<MediaMessageResponse> getMediaMessageResponseMediatorLiveData() {
        return mediaMessageResponseMediatorLiveData;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

}
