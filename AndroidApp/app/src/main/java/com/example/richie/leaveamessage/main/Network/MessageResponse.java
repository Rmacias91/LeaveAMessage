package com.example.richie.leaveamessage.main.Network;

import com.example.richie.leaveamessage.main.models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rmaci on 4/9/2018.
 */

public class MessageResponse {
    List<Message> messages;



    public MessageResponse(){
        messages = new ArrayList<>();
    }

    public static MessageResponse parseJSON(String response){
        GsonBuilder gsonBuilder = new GsonBuilder();
        String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();

        return gson.fromJson(response,MessageResponse.class);
    }
}
