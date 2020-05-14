package com.example.mychatlibrary.data.remote;

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

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @GET("chatrooms")
    Call<BaseResponse<List<GroupChatResponse>>> getGroupChatRooms();

    @GET("users")
    Call<BaseResponse<MyClassResponse>> getOneToOneChatRooms();

    @POST("chatrooms")
    Call<BaseResponse<CreateChatroomResponse>> createChatRoom(@Body CreateChatRoomRequest request);

    @DELETE("chatrooms/{chatroomId}")
    Call<BaseResponse<DeleteChatRoomResponse>> deleteChatRoom(@Path("chatroomId") int chatRoomId);

    @GET("chatrooms/joins_channel")
    Call<BaseResponse<WebinarResponse>> getJoinedChatRooms();

    @POST("chatrooms/{chatroomId}/chatroom_users")
    Call<BaseResponse<JoinChatRoomResponse>> joinChatRoom(@Path("chatroomId") int chatroomId);

    @DELETE("chatrooms/{chatroomId}/chatroom_users")
    Call<BaseResponse<LeaveChatroomResponse>> leaveChatRoom(@Path("chatroomId") int chatRoomId);

    @GET("chatrooms/{chatroomId}")
    Call<BaseResponse<MessagesResponseModel>> getChatRoomMessages(@Path("chatroomId") int chatRoomId);

    @GET("direct_messages/{userId}")
    Call<BaseResponse<MessagesResponseModel>> getUserMessages(@Path("userId") int userId);

    @Multipart
    @POST("chatrooms/{chatroomId}/messages")
    Call<BaseResponse<MediaMessageResponse>> sendMediaMessage(@Path("chatroomId") int chatRoomId,
                                                              @Part("message[media_type]") RequestBody type,
                                                              @Part MultipartBody.Part file);
}
