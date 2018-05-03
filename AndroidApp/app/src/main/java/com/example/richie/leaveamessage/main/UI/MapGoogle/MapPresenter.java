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
        //Need 7 decimals
        //TODO fixOverLAy of messages https://developers.google.com/maps/documentation/android-api/utility/marker-clustering
//        List<Message> mTestMessageList = new ArrayList<Message>() {{
//            add(new Message("Look here", "Made ya Look", "41.9456354", "-87.6679754"));
//            add(new Message("Eyyyyy", "You made it to the coolest block in the city!", "42.9525643", "-87.6542044"));
//            add(new Message("Free Dominos", "Yum pizza", "41.9525644", "-87.6542000"));
//            add(new Message("I grew up here", "Hope you guys enjoy house! Great Memories", "41.9456359", "-87.6679759"));
//        }};
//
//        return mTestMessageList;
    }

}