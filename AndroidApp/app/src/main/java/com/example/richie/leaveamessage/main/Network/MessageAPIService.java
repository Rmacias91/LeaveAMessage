package com.example.richie.leaveamessage.main.Network;

import com.example.richie.leaveamessage.main.Model.Message;

import java.util.List;

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

        @GET("message/")
        Call<List<Message>> getMessages();

        @GET("message/{id}")
        Call<Message> getMessage(@Path("id") int messageID);

        //Might need GSON to convert
        @POST("message")
        Call<Message> createMessage(@Body Message message);

        @DELETE("message/{id}")
        Call<Message> deleteMessage(@Path("id") int messageID);

        @PUT("message/{id}")
        Call<Message> updateMessage(@Path("id") int messageID);


}
