package com.example.richie.leaveamessage.main.Util;

import android.content.ContentValues;
import android.util.Log;

import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 5/3/2018.
 */

public class MessageUtil {

    public static final String TAG = MessageUtil.class.getSimpleName();

    public static ContentValues[] messagesToContentVals(List<Message> messages) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (Message message : messages) {
            ContentValues cv= new ContentValues();
            cv.put(MessageContract.MessageEntry.COLUMN_ID,message.getId());
            cv.put(MessageContract.MessageEntry.COLUMN_LAT,message.getLat());
            cv.put(MessageContract.MessageEntry.COLUMN_LON,message.getLon());
            cv.put(MessageContract.MessageEntry.COLUMN_DATE,message.getDate());
            cv.put(MessageContract.MessageEntry.COLUMN_MESSAGE,message.getMessage());
            Log.d(TAG,message.getId()+" "+message.getLat()+" " + message.getLon()+" "+ message.getDate()+" "+ message.getMessage());

        }
        ContentValues[] array = new ContentValues[contentValues.size()];
        return contentValues.toArray(array); // fill the array
    }
}
