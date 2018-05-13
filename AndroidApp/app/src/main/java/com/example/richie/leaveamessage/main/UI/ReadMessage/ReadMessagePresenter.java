package com.example.richie.leaveamessage.main.UI.ReadMessage;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.richie.leaveamessage.main.Network.MessageAPI;
import com.example.richie.leaveamessage.main.Network.MessageAPIService;
import com.example.richie.leaveamessage.main.Util.MessageUtil;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;

import java.util.List;

import retrofit2.http.Url;

/**
 * Created by Richie on 4/5/2018.
 */

public class ReadMessagePresenter implements ReadMessageContract.ReadMessagePresenter, MessageAPI.RequestListener {
    private final ReadMessageContract.ReadMessageView mView;
    private List<Message> mMessages;
    private ContentResolver mContentResolver;
    private int mCurrentId;
    private MessageAPI messageAPIService;

    public ReadMessagePresenter(ReadMessageContract.ReadMessageView view, ContentResolver contentResolver) {
        mView = view;
        mContentResolver = contentResolver;
        messageAPIService = new MessageAPI(this);
        refreshList();
    }


    private void refreshList() {

        Cursor cursor = mContentResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);
        mMessages = MessageUtil.cursorToList(cursor);
    }

    @Override
    public List<Message> getData() {
        return mMessages;
    }

    @Override
    public Message getOneMessage(int position) {
        Message message = mMessages.get(position);
        return message;
    }

    @Override
    public void deleteMessage() {
        int pos = mView.getCurrentMessagePos();
        mCurrentId = mMessages.get(pos).getId();
        messageAPIService.deleteMessage(mCurrentId);
    }


    @Override
    public void onSuccess(Object response) {
        Uri uri = MessageContract.MessageEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(mCurrentId)).build();
        int rowsDeleted = mContentResolver.delete(uri, null, null);
        if (rowsDeleted == 1) {
            mMessages.clear();
            refreshList();
            mView.showMessage("Deleted!");
            mView.finishRead();
        } else {
            mView.showMessage("Failed to Delete");
        }
    }

    @Override
    public void onFailure() {
        mView.showMessage("Failed to delete message");
    }
}
