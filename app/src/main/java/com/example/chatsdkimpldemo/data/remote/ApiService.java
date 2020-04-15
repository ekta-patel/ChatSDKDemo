package com.example.chatsdkimpldemo.data.remote;



import com.example.mychatlibrary.data.models.request.createchatroom.CreateChatRoomRequest;
import com.example.mychatlibrary.data.models.request.login.LoginRequest;
import com.example.mychatlibrary.data.models.request.logout.LogoutRequest;
import com.example.mychatlibrary.data.models.request.register.RegisterRequestModel;
import com.example.mychatlibrary.data.models.response.createchatroom.CreateChatroomResponse;
import com.example.mychatlibrary.data.models.response.deletechatroom.DeleteChatRoomResponse;
import com.example.mychatlibrary.data.models.response.groupchat.GroupChatResponse;
import com.example.mychatlibrary.data.models.response.joinchatroom.JoinChatRoomResponse;
import com.example.mychatlibrary.data.models.response.joinedgroups.JoinedChatRoomResponse;
import com.example.mychatlibrary.data.models.response.leavechatroom.LeaveChatroomResponse;
import com.example.mychatlibrary.data.models.response.login.LoginResponse;
import com.example.mychatlibrary.data.models.response.logout.LogoutResponse;
import com.example.mychatlibrary.data.models.response.messages.MessagesResponseModel;
import com.example.mychatlibrary.data.models.response.one2onechat.OneToOneChatResponse;
import com.example.mychatlibrary.data.models.response.register.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("oauth/token")
    Call<LoginResponse> callLogin(@Body LoginRequest request);

    @POST("users")
    Call<RegisterResponse> callRegister(@Body RegisterRequestModel register);

    @GET("chatrooms")
    Call<List<GroupChatResponse>> getGroupChatRooms();

    @GET("users")
    Call<List<OneToOneChatResponse>> getOneToOneChatRooms();

    @POST("oauth/revoke")
    Call<LogoutResponse> callLogout(@Body LogoutRequest request);

    @POST("chatrooms")
    Call<CreateChatroomResponse> createChatRoom(@Body CreateChatRoomRequest request);

    @DELETE("chatrooms/{chatroomId}")
    Call<DeleteChatRoomResponse> deleteChatRoom(@Path("chatroomId") int chatRoomId);

    @GET("chatrooms/joins_channel")
    Call<List<JoinedChatRoomResponse>> getJoinedChatRooms();

    @POST("chatrooms/{chatroomId}/chatroom_users")
    Call<JoinChatRoomResponse> joinChatRoom(@Path("chatroomId") int chatroomId);

    @DELETE("chatrooms/{chatroomId}/chatroom_users")
    Call<LeaveChatroomResponse> leaveChatRoom(@Path("chatroomId") int chatRoomId);

    @GET("chatrooms/{chatroomId}")
    Call<MessagesResponseModel> getChatRoomMessages(@Path("chatroomId") int chatRoomId);

    @GET("direct_messages/{userId}")
    Call<MessagesResponseModel> getUserMessages(@Path("userId") int userId);
}
