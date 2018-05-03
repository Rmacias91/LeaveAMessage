package com.example.richie.leaveamessage.main.Util;

import android.content.ContentValues;
import android.database.Cursor;
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
            contentValues.add(cv);
        }
        ContentValues[] array = new ContentValues[contentValues.size()];
        return contentValues.toArray(array); // fill the array
    }

    public static List<Message> cursorToList(Cursor cursor){
        List<Message> messages = new ArrayList<>();
        //add(new Message("Eyyyyy", "You made it to the coolest block in the city!", "42.9525643", "-87.6542044"));
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_ID));
            String title = "Message: "+id;
            double lat = cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LAT));
            double lon = cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LON));
            String date = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_DATE));
            String message = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_MESSAGE));
            messages.add(new Message(title,id,lat,lon,date,message));
        }
        return messages;
    }
}
