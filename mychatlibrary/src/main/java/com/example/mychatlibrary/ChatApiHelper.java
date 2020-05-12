package com.example.mychatlibrary;

import androidx.lifecycle.MutableLiveData;

import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.messages.MediaMessageResponse;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.myclass.MyClassResponse;
import com.example.mychatlibrary.data.models.response.webinar.WebinarResponse;
import com.example.mychatlibrary.data.remote.ApiClient;
import com.example.mychatlibrary.data.remote.ApiService;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public final class ChatApiHelper {

    private static ChatApiHelper INSTANCE;
    private ApiService apiService = ApiClient.getApiService();

    static ChatApiHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatApiHelper();
        }
        return INSTANCE;
    }

    MutableLiveData<MyClassResponse> getOneToOneChatRooms() {
        MutableLiveData<MyClassResponse> data = new MutableLiveData<>();
        apiService.getOneToOneChatRooms().enqueue(new Callback<MyClassResponse>() {
            @Override
            public void onResponse(Call<MyClassResponse> call, Response<MyClassResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<MyClassResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<List<GroupChatResponse>> getGroupChatRooms() {
        MutableLiveData<List<GroupChatResponse>> data = new MutableLiveData<>();
        apiService.getGroupChatRooms().enqueue(new Callback<List<GroupChatResponse>>() {
            @Override
            public void onResponse(Call<List<GroupChatResponse>> call, Response<List<GroupChatResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<List<GroupChatResponse>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<CreateChatroomResponse> createChatRoom(CreateChatRoomRequest request) {
        MutableLiveData<CreateChatroomResponse> data = new MutableLiveData<>();
        apiService.createChatRoom(request).enqueue(new Callback<CreateChatroomResponse>() {
            @Override
            public void onResponse(Call<CreateChatroomResponse> call, Response<CreateChatroomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<CreateChatroomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<DeleteChatRoomResponse> deleteChatRoom(int chatRoomId) {
        MutableLiveData<DeleteChatRoomResponse> data = new MutableLiveData<>();
        apiService.deleteChatRoom(chatRoomId).enqueue(new Callback<DeleteChatRoomResponse>() {
            @Override
            public void onResponse(Call<DeleteChatRoomResponse> call, Response<DeleteChatRoomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<DeleteChatRoomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<WebinarResponse> getJoinedChatRooms() {
        MutableLiveData<WebinarResponse> data = new MutableLiveData<>();
        apiService.getJoinedChatRooms().enqueue(new Callback<WebinarResponse>() {
            @Override
            public void onResponse(Call<WebinarResponse> call, Response<WebinarResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<WebinarResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<JoinChatRoomResponse> joinChatRoom(int chatroomId) {
        MutableLiveData<JoinChatRoomResponse> data = new MutableLiveData<>();
        apiService.joinChatRoom(chatroomId).enqueue(new Callback<JoinChatRoomResponse>() {
            @Override
            public void onResponse(Call<JoinChatRoomResponse> call, Response<JoinChatRoomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<JoinChatRoomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<LeaveChatroomResponse> leaveChatRoom(int chatRoomId) {
        MutableLiveData<LeaveChatroomResponse> data = new MutableLiveData<>();
        apiService.leaveChatRoom(chatRoomId).enqueue(new Callback<LeaveChatroomResponse>() {
            @Override
            public void onResponse(Call<LeaveChatroomResponse> call, Response<LeaveChatroomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<LeaveChatroomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<MessagesResponseModel> getChatRoomMessages(int chatRoomId) {
        MutableLiveData<MessagesResponseModel> data = new MutableLiveData<>();
        apiService.getChatRoomMessages(chatRoomId).enqueue(new Callback<MessagesResponseModel>() {
            @Override
            public void onResponse(Call<MessagesResponseModel> call, Response<MessagesResponseModel> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<MessagesResponseModel> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<MessagesResponseModel> getUserMessages(int userId) {
        MutableLiveData<MessagesResponseModel> data = new MutableLiveData<>();
        apiService.getUserMessages(userId).enqueue(new Callback<MessagesResponseModel>() {
            @Override
            public void onResponse(Call<MessagesResponseModel> call, Response<MessagesResponseModel> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<MessagesResponseModel> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    MutableLiveData<MediaMessageResponse> sendMediaMessage(int chatRoomId, File f, MediaType mediaType) {
        MutableLiveData<MediaMessageResponse> data = new MutableLiveData<>();
        RequestBody requestFile = RequestBody.create(f, mediaType);
        MultipartBody.Part body = MultipartBody.Part.createFormData("message[file]", f.getName(), requestFile);
        apiService.sendMediaMessage(chatRoomId, body).enqueue(new Callback<MediaMessageResponse>() {
            @Override
            public void onResponse(Call<MediaMessageResponse> call, Response<MediaMessageResponse> response) {
                if (response.code() == 201)
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<MediaMessageResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

}
