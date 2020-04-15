package com.example.chatsdkimpldemo.ui.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatsdkimpldemo.utils.Event;
import com.example.mychatlibrary.ConfigChatSocket;
import com.example.mychatlibrary.actioncables.Message;
import com.example.mychatlibrary.data.local.AppSharedPrefManager;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.one2onechat.OneToOneChatResponse;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {

    private ConfigChatSocket chatSocket;
    private MutableLiveData<Message> messageMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private MediatorLiveData<DeleteChatRoomResponse> deleteChatRoomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<LeaveChatroomResponse> leaveChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<MessagesResponseModel> messagesResponseModelMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<OneToOneChatResponse> getOneToOneChatRoomsMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<GroupChatResponse>> getGroupChatRoomsMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Event<CreateChatroomResponse>> createChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<JoinedChatRoomResponse>> joinedChatroomResponseMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<JoinChatRoomResponse> joinChatroomResponseMediatorLiveData = new MediatorLiveData<>();

    void connectWebSocket() {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "bearer " + AppSharedPrefManager.getToken());
            chatSocket = new ConfigChatSocket.Builder("ws://13.235.232.157/cable", "ChatroomsChannel", new HashMap<String, String>()).headers(headers).addChatListener(new ConfigChatSocket.ChatCallback() {
                @Override
                public void onConnected() {
                    //TODO: observ on connect
                }

                @Override
                public void onDisConnected() {
                    //TODO: observ on disconnect
                }

                @Override
                public void onRejected() {
                    //TODO: observ on reject
                }

                @Override
                public void onFailed(Throwable t) {
                    //TODO: observ on failed

                }

                @Override
                public <T> void onReceived(T any) {
//                    messageMutableLiveData.postValue(T);
                }
            }).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
            createChatroomResponseMediatorLiveData.postValue(new Event<CreateChatroomResponse>(response));
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

    public LiveData<OneToOneChatResponse> getOneToOneChatResponseLiveData() {
        return getOneToOneChatRoomsMediatorLiveData;
    }

    public LiveData<List<GroupChatResponse>> getGroupChatResponseLiveData() {
        return getGroupChatRoomsMediatorLiveData;
    }

    public LiveData<Event<CreateChatroomResponse>> getCreateChatRoomResponseLiveData() {
        return createChatroomResponseMediatorLiveData;
    }

    public LiveData<List<JoinedChatRoomResponse>> getJoinedChatRoomResponseLiveData() {
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
