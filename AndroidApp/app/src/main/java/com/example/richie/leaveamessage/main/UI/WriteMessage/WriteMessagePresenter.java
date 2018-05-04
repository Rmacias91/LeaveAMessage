package com.example.richie.leaveamessage.main.UI.WriteMessage;

import android.content.ContentResolver;

import com.example.richie.leaveamessage.main.Network.MessageAPI;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;

/**
 * Created by Richie on 5/4/2018.
 */

public class WriteMessagePresenter implements WriteMessageContract.presenter, MessageAPI.RequestListener{
    private WriteMessageContract.view mView;
    private ContentResolver mContentResolver;
    private MessageAPI messageAPIService;


    public WriteMessagePresenter(WriteMessageContract.view view, ContentResolver contentResolver){
        this.mView = view;
        this.mContentResolver = contentResolver;
        messageAPIService = new MessageAPI(this);
    }
    @Override
    public void saveMessage(Message message) {
        messageAPIService.createMessage(message);
    }

    @Override
    public void onSuccess(Object response) {
        int id = (int) response;
        
        mContentResolver.insert(MessageContract.MessageEntry.CONTENT_URI,)

    }

    @Override
    public void onFailure() {
        mView.showMessage("Error Saving message");
    }
}