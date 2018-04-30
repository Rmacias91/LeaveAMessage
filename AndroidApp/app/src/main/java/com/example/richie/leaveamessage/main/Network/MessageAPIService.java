package com.example.richie.leaveamessage.main.Network;

import com.example.richie.leaveamessage.main.models.Message;
import com.example.richie.leaveamessage.main.models.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("message/{id}")
    Call<MessageResponse> getMessage(@Path("id") int messageID);

    @POST("message")
    Call<MessageResponse> createMessage(@Body Message message);

    @DELETE("message/{id}")
    Call<MessageResponse> deleteMessage(@Path("id") int messageID);

    @PUT("message/{id}")
    Call<MessageResponse> updateMessage(@Path("id") int messageID,@Body Message message);


}
