package com.example.mychatlibrary;

import androidx.lifecycle.MutableLiveData;

import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.mychatlibrary.data.remote.ApiClient;
import com.example.mychatlibrary.data.remote.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public final class ChatApiHelper {

    private static ChatApiHelper INSTANCE;
    private ApiService apiService = ApiClient.getApiService();

    public static ChatApiHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatApiHelper();
        }
        return INSTANCE;
    }
//
//    public <T> MutableLiveData<T> getOneToOneChatRooms() {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>getOneToOneChatRooms().enqueue(new Callback<T>() {
//
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful()) {
//                    data.postValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> getGroupChatRooms() {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>getGroupChatRooms().enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T, U> MutableLiveData<T> createChatRoom(U request) {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T, U>createChatRoom(request).enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> deleteChatRoom(int chatRoomId) {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>deleteChatRoom(chatRoomId).enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> getJoinedChatRooms() {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>getJoinedChatRooms().enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> joinChatRoom(int chatRoomId) {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>joinChatRoom(chatRoomId).enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> leaveChatRoom(int chatRoomId) {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>leaveChatRoom(chatRoomId).enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> getChatRoomMessages(int chatRoomId) {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>getChatRoomMessages(chatRoomId).enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }
//
//    public <T> MutableLiveData<T> getUserMessages(int userId) {
//        MutableLiveData<T> data = new MutableLiveData<>();
//        apiService.<T>getUserMessages(userId).enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (response.isSuccessful())
//                    data.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                data.postValue(null);
//            }
//        });
//        return data;
//    }


    public MutableLiveData<OneToOneChatResponse> getOneToOneChatRooms() {
        MutableLiveData<OneToOneChatResponse> data = new MutableLiveData<>();
        apiService.getOneToOneChatRooms().enqueue(new Callback<OneToOneChatResponse>() {
            @Override
            public void onResponse(Call<OneToOneChatResponse> call, Response<OneToOneChatResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
                else
                    data.postValue(null);
            }

            @Override
            public void onFailure(Call<OneToOneChatResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<GroupChatResponse>> getGroupChatRooms() {
        MutableLiveData<List<GroupChatResponse>> data = new MutableLiveData<>();
        apiService.getGroupChatRooms().enqueue(new Callback<List<GroupChatResponse>>() {
            @Override
            public void onResponse(Call<List<GroupChatResponse>> call, Response<List<GroupChatResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GroupChatResponse>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<CreateChatroomResponse> createChatRoom(CreateChatRoomRequest request) {
        MutableLiveData<CreateChatroomResponse> data = new MutableLiveData<>();
        apiService.createChatRoom(request).enqueue(new Callback<CreateChatroomResponse>() {
            @Override
            public void onResponse(Call<CreateChatroomResponse> call, Response<CreateChatroomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CreateChatroomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<DeleteChatRoomResponse> deleteChatRoom(int chatRoomId) {
        MutableLiveData<DeleteChatRoomResponse> data = new MutableLiveData<>();
        apiService.deleteChatRoom(chatRoomId).enqueue(new Callback<DeleteChatRoomResponse>() {
            @Override
            public void onResponse(Call<DeleteChatRoomResponse> call, Response<DeleteChatRoomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<DeleteChatRoomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<JoinedChatRoomResponse>> getJoinedChatRooms() {
        MutableLiveData<List<JoinedChatRoomResponse>> data = new MutableLiveData<>();
        apiService.getJoinedChatRooms().enqueue(new Callback<List<JoinedChatRoomResponse>>() {
            @Override
            public void onResponse(Call<List<JoinedChatRoomResponse>> call, Response<List<JoinedChatRoomResponse>> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<JoinedChatRoomResponse>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<JoinChatRoomResponse> joinChatRoom(int chatroomId) {
        MutableLiveData<JoinChatRoomResponse> data = new MutableLiveData<>();
        apiService.joinChatRoom(chatroomId).enqueue(new Callback<JoinChatRoomResponse>() {
            @Override
            public void onResponse(Call<JoinChatRoomResponse> call, Response<JoinChatRoomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<JoinChatRoomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<LeaveChatroomResponse> leaveChatRoom(int chatRoomId) {
        MutableLiveData<LeaveChatroomResponse> data = new MutableLiveData<>();
        apiService.leaveChatRoom(chatRoomId).enqueue(new Callback<LeaveChatroomResponse>() {
            @Override
            public void onResponse(Call<LeaveChatroomResponse> call, Response<LeaveChatroomResponse> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LeaveChatroomResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<MessagesResponseModel> getChatRoomMessages(int chatRoomId) {
        MutableLiveData<MessagesResponseModel> data = new MutableLiveData<>();
        apiService.getChatRoomMessages(chatRoomId).enqueue(new Callback<MessagesResponseModel>() {
            @Override
            public void onResponse(Call<MessagesResponseModel> call, Response<MessagesResponseModel> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MessagesResponseModel> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<MessagesResponseModel> getUserMessages(int userId) {
        MutableLiveData<MessagesResponseModel> data = new MutableLiveData<>();
        apiService.getUserMessages(userId).enqueue(new Callback<MessagesResponseModel>() {
            @Override
            public void onResponse(Call<MessagesResponseModel> call, Response<MessagesResponseModel> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MessagesResponseModel> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

}
