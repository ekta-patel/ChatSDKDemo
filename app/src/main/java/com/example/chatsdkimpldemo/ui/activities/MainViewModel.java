package com.example.chatsdkimpldemo.ui.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mychatlibrary.ChatSocketBuilder;
import com.example.mychatlibrary.ConfigChatSocket;
import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.chatroomdetails.ChatroomDetailsResponseModel;
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

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private ConfigChatSocket chatSocket;
    private ChatSocketBuilder.ChatCallback defaultChannelListener = new ChatSocketBuilder.ChatCallback() {

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

    private ChatSocketBuilder.ChatCallback newChannelListener = new ChatSocketBuilder.ChatCallback() {
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

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    void createDefaultChatSocket() {
        try {
            Map<String, String> options = new HashMap<>();
            try {
                options.put("login", "useremail@gmail.com");
                options.put("password", MD5.getMd5("Tatva@123"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            chatSocket = new ChatSocketBuilder(getApplication(), "ws://13.235.232.157/cable", "ChatroomsChannel").query(options).addChatListener(defaultChannelListener).build();
        } catch (
                URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private MutableLiveData<Message> messageMutableLiveData = new MutableLiveData<>();

    public ConfigChatSocket getChatSocket() {
        return chatSocket;
    }

    public void connectWebSocket(Map<String, String> v) {
        chatSocket.connect(v);
    }

    public void disconnectWebSocket() {
        chatSocket.disconnect();
    }

    public LiveData<BaseResponse<DeleteChatRoomResponse>> deleteChatroom(int chatRoomId) {
        return chatSocket.deleteChatroom(chatRoomId);
    }

    public LiveData<BaseResponse<LeaveChatroomResponse>> leaveChatroom(int chatRoomId) {
        return chatSocket.leaveChatroom(chatRoomId);
    }

    public LiveData<BaseResponse<MessagesResponseModel>> getChatRoomMessages(int chatRoomId) {
        return chatSocket.getChatRoomMessages(chatRoomId);
    }

    public LiveData<BaseResponse<MessagesResponseModel>> getUserMessages(int userId) {
        return chatSocket.getUserMessages(userId);
    }

    public LiveData<BaseResponse<WebinarResponse>> getJoinedChatRooms() {
        return chatSocket.getJoinedChatRooms();
    }

    public LiveData<BaseResponse<List<GroupChatResponse>>> getChatRooms() {
        return chatSocket.getGroupChatRooms();
    }

    public LiveData<BaseResponse<CreateChatroomResponse>> createChatRoom(CreateChatRoomRequest request) {
        return chatSocket.createChatRoom(request);
    }

    public LiveData<BaseResponse<JoinChatRoomResponse>> joinChatRoom(int chatroomId) {
        return chatSocket.joinChatRoom(chatroomId);
    }

    public LiveData<BaseResponse<MyClassResponse>> getMyClassRooms() {
        return chatSocket.getOneToOneChatRooms();
    }

    public LiveData<BaseResponse<MediaMessageResponse>> sendMediaMessage(int chatroomId, File f, MediaType mediaType) {
        return chatSocket.sendMediaMessage(chatroomId, f, mediaType);
    }

    public LiveData<BaseResponse<ChatroomDetailsResponseModel>> getChatroomDetails(int chatroomId) {
        return chatSocket.getChatroomDetails(chatroomId);
    }

    public LiveData<Message> getMessage() {
        return messageMutableLiveData;
    }
}
