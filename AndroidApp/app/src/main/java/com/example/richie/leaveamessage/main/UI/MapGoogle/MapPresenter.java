package com.example.richie.leaveamessage.main.UI.MapGoogle;


import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import com.example.richie.leaveamessage.main.Util.MessageUtil;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 4/3/2018.
 */

public class MapPresenter implements MapContract.PresenterMap {
    public static final String TAG = MapPresenter.class.getSimpleName();
    private MapContract.ViewMap view;
    private ContentResolver mContentResolver;


    MapPresenter(MapContract.ViewMap view, ContentResolver contentResolver){
        mContentResolver = contentResolver;
    }


    @Override
    public List<Message> getData() {

        Cursor cursor = mContentResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);



        return MessageUtil.cursorToList(cursor);
        //TODO fixOverLAy of messages https://developers.google.com/maps/documentation/android-api/utility/marker-clustering
    }

}