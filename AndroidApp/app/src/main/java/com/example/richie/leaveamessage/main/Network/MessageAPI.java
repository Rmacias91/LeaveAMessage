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
    private List<Message> messages;


    public MessageAPI() {
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

    public List<Message> getMessages() {
        Call<MessageResponse> call = service.getMessages();
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                //if status code is good. and api returns success
                if (response.code() == 200) {
                    if (response.body().getSuccess()) {
                        messages = response.body().getMessages();
                    }
                    Log.d(TAG, "SUCCESS!" + messages.get(0).getMessage());
                }
            }

            //4/10/18 I was able to fix my LEEco phone to Log! I may cry... dial *#*#76937#*#*
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return messages;
    }


}
