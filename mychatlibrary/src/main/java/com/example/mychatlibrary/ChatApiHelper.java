package com.example.mychatlibrary;

import androidx.lifecycle.MutableLiveData;

import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.response.base.BaseResponse;
import com.example.mychatlibrary.data.models.response.base.Status;
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

final class ChatApiHelper {

    private static ChatApiHelper INSTANCE;
    private ApiService apiService = ApiClient.getApiService();

    static ChatApiHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatApiHelper();
        }
        return INSTANCE;
    }

    MutableLiveData<BaseResponse<MyClassResponse>> getOneToOneChatRooms() {
        MutableLiveData<BaseResponse<MyClassResponse>> data = new MutableLiveData<>();
        data.postValue(new BaseResponse<>(Status.LOADING, null));
        apiService.getOneToOneChatRooms().enqueue(new Callback<BaseResponse<MyClassResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MyClassResponse>> call, Response<BaseResponse<MyClassResponse>> response) {
                BaseResponse<MyClassResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MyClassResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<List<GroupChatResponse>>> getGroupChatRooms() {
        MutableLiveData<BaseResponse<List<GroupChatResponse>>> data = new MutableLiveData<>();
        data.postValue(new BaseResponse<>(Status.LOADING, null));
        apiService.getGroupChatRooms().enqueue(new Callback<BaseResponse<List<GroupChatResponse>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<GroupChatResponse>>> call, Response<BaseResponse<List<GroupChatResponse>>> response) {
                BaseResponse<List<GroupChatResponse>> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<GroupChatResponse>>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<CreateChatroomResponse>> createChatRoom(CreateChatRoomRequest request) {
        MutableLiveData<BaseResponse<CreateChatroomResponse>> data = new MutableLiveData<>();
        data.postValue(new BaseResponse<>(Status.LOADING, null));
        apiService.createChatRoom(request).enqueue(new Callback<BaseResponse<CreateChatroomResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<CreateChatroomResponse>> call, Response<BaseResponse<CreateChatroomResponse>> response) {
                BaseResponse<CreateChatroomResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CreateChatroomResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<DeleteChatRoomResponse>> deleteChatRoom(int chatRoomId) {
        MutableLiveData<BaseResponse<DeleteChatRoomResponse>> data = new MutableLiveData<>();
        apiService.deleteChatRoom(chatRoomId).enqueue(new Callback<BaseResponse<DeleteChatRoomResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<DeleteChatRoomResponse>> call, Response<BaseResponse<DeleteChatRoomResponse>> response) {
                BaseResponse<DeleteChatRoomResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<DeleteChatRoomResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<WebinarResponse>> getJoinedChatRooms() {
        MutableLiveData<BaseResponse<WebinarResponse>> data = new MutableLiveData<>();
        apiService.getJoinedChatRooms().enqueue(new Callback<BaseResponse<WebinarResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<WebinarResponse>> call, Response<BaseResponse<WebinarResponse>> response) {
                BaseResponse<WebinarResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<WebinarResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<JoinChatRoomResponse>> joinChatRoom(int chatroomId) {
        MutableLiveData<BaseResponse<JoinChatRoomResponse>> data = new MutableLiveData<>();
        apiService.joinChatRoom(chatroomId).enqueue(new Callback<BaseResponse<JoinChatRoomResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<JoinChatRoomResponse>> call, Response<BaseResponse<JoinChatRoomResponse>> response) {
                BaseResponse<JoinChatRoomResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<JoinChatRoomResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<LeaveChatroomResponse>> leaveChatRoom(int chatRoomId) {
        MutableLiveData<BaseResponse<LeaveChatroomResponse>> data = new MutableLiveData<>();
        apiService.leaveChatRoom(chatRoomId).enqueue(new Callback<BaseResponse<LeaveChatroomResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<LeaveChatroomResponse>> call, Response<BaseResponse<LeaveChatroomResponse>> response) {
                BaseResponse<LeaveChatroomResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LeaveChatroomResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<MessagesResponseModel>> getChatRoomMessages(int chatRoomId) {
        MutableLiveData<BaseResponse<MessagesResponseModel>> data = new MutableLiveData<>();
        apiService.getChatRoomMessages(chatRoomId).enqueue(new Callback<BaseResponse<MessagesResponseModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<MessagesResponseModel>> call, Response<BaseResponse<MessagesResponseModel>> response) {
                BaseResponse<MessagesResponseModel> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MessagesResponseModel>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<MessagesResponseModel>> getUserMessages(int userId) {
        MutableLiveData<BaseResponse<MessagesResponseModel>> data = new MutableLiveData<>();
        data.postValue(new BaseResponse<>(Status.LOADING, null));
        apiService.getUserMessages(userId).enqueue(new Callback<BaseResponse<MessagesResponseModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<MessagesResponseModel>> call, Response<BaseResponse<MessagesResponseModel>> response) {
                BaseResponse<MessagesResponseModel> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MessagesResponseModel>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

    MutableLiveData<BaseResponse<MediaMessageResponse>> sendMediaMessage(int chatRoomId, File f, MediaType mediaType) {
        MutableLiveData<BaseResponse<MediaMessageResponse>> data = new MutableLiveData<>();
        RequestBody requestFile = RequestBody.create(f, mediaType);
        MultipartBody.Part body = MultipartBody.Part.createFormData("message[file]", f.getName(), requestFile);
        String descriptionString = mediaType.type();
        RequestBody description =
                RequestBody.create(descriptionString, MediaType.parse("text/plain"));
        apiService.sendMediaMessage(chatRoomId, description, body).enqueue(new Callback<BaseResponse<MediaMessageResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<MediaMessageResponse>> call, Response<BaseResponse<MediaMessageResponse>> response) {
                BaseResponse<MediaMessageResponse> r = response.body();
                if (r != null) {
                    if (r.isSuccess()) {
                        data.postValue(new BaseResponse<>(Status.SUCCESS, r.getData()));
                    } else {
                        data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(r.getError())));
                    }
                } else {
                    data.postValue(new BaseResponse<>(Status.FAILURE, new Throwable(response.message())));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MediaMessageResponse>> call, Throwable t) {
                data.postValue(new BaseResponse<>(Status.FAILURE, t));
            }
        });
        return data;
    }

}
