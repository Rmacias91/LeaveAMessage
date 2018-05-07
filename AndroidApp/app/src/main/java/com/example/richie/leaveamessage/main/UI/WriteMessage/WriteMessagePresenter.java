package com.example.richie.leaveamessage.main.UI.WriteMessage;

import android.content.ContentResolver;
import android.net.Uri;

import com.example.richie.leaveamessage.main.Network.MessageAPI;
import com.example.richie.leaveamessage.main.Util.MessageUtil;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Richie on 5/4/2018.
 */

public class WriteMessagePresenter implements WriteMessageContract.presenter, MessageAPI.RequestListener {
    private WriteMessageContract.view mView;
    private ContentResolver mContentResolver;
    private MessageAPI messageAPIService;
    private Message mSavedMessage;


    public WriteMessagePresenter(WriteMessageContract.view view, ContentResolver contentResolver) {
        this.mView = view;
        this.mContentResolver = contentResolver;
        messageAPIService = new MessageAPI(this);
    }

    @Override
    public void saveMessage(String message, double lat, double lon) {
        String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
        String date = new SimpleDateFormat(ISO_FORMAT, Locale.US).format(new Date());
        mSavedMessage = new Message("", 0, lat, lon, date, message);
        messageAPIService.createMessage(mSavedMessage);
    }

    @Override
    public void onSuccess(Object response) {
        int id = (int) response;
        mSavedMessage.setId(id);
        List<Message> messages = new ArrayList<>();
        messages.add(mSavedMessage);
        Uri uri = MessageContract.MessageEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        mContentResolver.insert(uri, MessageUtil.messagesToContentVals(messages)[0]);
        mView.activityFinish();
    }

    @Override
    public void onFailure() {
        mView.showMessage("Error Saving message");
    }
}