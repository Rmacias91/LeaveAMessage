package com.example.richie.leaveamessage.main.Network;

import android.util.Log;

import com.example.richie.leaveamessage.main.models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Richie on 4/9/2018.
 */

public class MessageAPI {
    public static final String TAG = MessageAPI.class.getSimpleName();
public static final String BASE_URL = "http://default-environment.dber9pmkbe.us-east-2.elasticbeanstalk.com/";
    public MessageAPIService service;
    private Message messages;
    public MessageAPI(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         service = retrofit.create(MessageAPIService.class);
    }

    public Message getMessages(){
        Call<Message> call = service.getMessages();
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                int statusCode = response.code();
                //if status code is good.
                messages = response.body();
                Log.d(TAG,"SUCCESS!"+ messages.getMessages().get(0));
            }
//4/10/18 I was able to fix my LEEco phone to Log! I may cry... dial *#*#76937#*#*
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
        return messages;
    }




}
