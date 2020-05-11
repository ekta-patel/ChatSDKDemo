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
import com.example.mychatlibrary.data.models.response.messages.Message;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.myclass.MyClassResponse;
import com.example.mychatlibrary.data.models.response.webinar.WebinarResponse;
import com.example.mychatlibrary.utils.MD5;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private ConfigChatSocket chatSocket;

    {
        try {
            Map<String, String> options = new HashMap<>();
            try {
                options.put("login", "useremail@gmail.com");
                options.put("password", MD5.getMd5("Tatva@123"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            chatSocket = new ConfigChatSocket.Builder("ws://13.235.232.157/cable", "ChatroomsChannel").query(options).addChatListener(new ConfigChatSocket.ChatCallback() {
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
                                        messageMutableLiveData.postValue(message1);
                                    }
                                }
                            } else {

                            }
                        }
                    }
                }
            }).build();
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

    public ConfigChatSocket getChatSocket() {
        return chatSocket;
    }

    void connectWebSocket() {
        chatSocket.connect();
    }

    void disconnectWebSocket() {
        chatSocket.disconnect();
    }

    public void deleteChatroom(int chatRoomId) {
        _isLoading.postValue(true);
        deleteChatRoomResponseMediatorLiveData.addSource(chatSocket.deleteChatroom(chatRoomId), response -> {
            deleteChatRoomResponseMediatorLiveData.postValue(response);
        });
    }

    public void leaveChatroom(int chatRoomId) {
        _isLoading.postValue(true);
        leaveChatroomResponseMediatorLiveData.addSource(chatSocket.leaveChatroom(chatRoomId), leaveChatroomResponse -> {
            _isLoading.postValue(false);
            leaveChatroomResponseMediatorLiveData.postValue(leaveChatroomResponse);
        });
    }

    public void getChatRoomMessages(int chatRoomId) {
        _isLoading.postValue(true);
        messagesResponseModelMediatorLiveData.addSource(chatSocket.getChatRoomMessages(chatRoomId), messagesResponseModel -> {
            _isLoading.postValue(false);
            messagesResponseModelMediatorLiveData.postValue(messagesResponseModel);
        });
    }

    public void getUserMessages(int userId) {
        _isLoading.postValue(true);
        messagesResponseModelMediatorLiveData.addSource(chatSocket.getUserMessages(userId), messagesResponseModel -> {
            _isLoading.postValue(false);
            messagesResponseModelMediatorLiveData.postValue(messagesResponseModel);
        });
    }

    public void getJoinedChatRooms() {
        _isLoading.postValue(true);
        joinedChatroomResponseMediatorLiveData.addSource(chatSocket.getJoinedChatRooms(), response -> {
            _isLoading.postValue(false);
            joinedChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public void getChatRooms() {
        _isLoading.postValue(true);
        getGroupChatRoomsMediatorLiveData.addSource(chatSocket.getGroupChatRooms(), response -> {
            _isLoading.postValue(false);
            getGroupChatRoomsMediatorLiveData.postValue(response);
        });
    }

    public void createChatRoom(CreateChatRoomRequest request) {
        _isLoading.postValue(true);
        createChatroomResponseMediatorLiveData.addSource(chatSocket.createChatRoom(request), response -> {
            _isLoading.postValue(false);
            createChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public void joinChatRoom(int chatroomId) {
        _isLoading.postValue(true);
        joinChatroomResponseMediatorLiveData.addSource(chatSocket.joinChatRoom(chatroomId), response -> {
            _isLoading.postValue(false);
            joinChatroomResponseMediatorLiveData.postValue(response);
        });
    }

    public void getOneToOneChatRooms() {
        _isLoading.postValue(true);
        getOneToOneChatRoomsMediatorLiveData.addSource(chatSocket.getOneToOneChatRooms(), response -> {
            _isLoading.postValue(false);
            getOneToOneChatRoomsMediatorLiveData.postValue(response);
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

    public MutableLiveData<Message> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }

}
