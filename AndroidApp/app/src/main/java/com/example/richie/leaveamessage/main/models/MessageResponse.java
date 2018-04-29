package com.example.richie.leaveamessage.main.models;

import com.example.richie.leaveamessage.main.models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rmaci on 4/9/2018.
 */

public class MessageResponse {
    private Boolean success;
    private List<Message> messages;
    private String message;


    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<Message> getMessages() {
        return messages;
    }




}