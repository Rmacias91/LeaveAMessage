package com.example.richie.leaveamessage.main.UI.ReadMessage;

import android.content.ContentResolver;
import android.database.Cursor;

import com.example.richie.leaveamessage.main.Util.MessageUtil;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;

import java.util.List;

/**
 * Created by Richie on 4/5/2018.
 */

public class ReadMessagePresenter implements ReadMessageContract.ReadMessagePresenter {
    private final ReadMessageContract.ReadMessageView mView;
    private List<Message> mMessages;
    private ContentResolver mContentResolver;

    public ReadMessagePresenter(ReadMessageContract.ReadMessageView view, ContentResolver contentResolver){
        mView = view;
        mContentResolver = contentResolver;
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
        return mMessages.get(position);
    }


}
