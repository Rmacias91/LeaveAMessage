package com.example.richie.leaveamessage.main.Network;

import com.example.richie.leaveamessage.main.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Richie on 4/9/2018.
 */

public interface MessageAPIService {

    @GET("message")
    Call<MessageResponse> getMessages();

    //TODO I think I'm getting an error on API where message response is null and never prints message
    @GET("message/{id}")
    Call<MessageResponse> getMessage(@Path("id") int messageID);

    //TODO Update API to return ID of newly created post. No way to identify post id
    @POST("message")
    Call<MessageResponse> createMessage(@Body Message message);

    @DELETE("message/{id}")
    Call<MessageResponse> deleteMessage(@Path("id") int messageID);

    @PUT("message/{id}")
    Call<MessageResponse> updateMessage(@Path("id") int messageID,@Body Message message);


}
