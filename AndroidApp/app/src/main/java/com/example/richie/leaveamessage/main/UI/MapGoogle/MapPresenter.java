package com.example.richie.leaveamessage.main.UI.MapGoogle;


import android.content.ContentResolver;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;

import com.example.richie.leaveamessage.main.Util.MessageUtil;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;
import java.util.Random;

/**
 * Created by Richie on 4/3/2018.
 */

public class MapPresenter implements MapContract.PresenterMap {
    public static final String TAG = MapPresenter.class.getSimpleName();
    private MapContract.ViewMap view;
    private ContentResolver mContentResolver;
    private List<Message> mMessages;


    MapPresenter(MapContract.ViewMap view, ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }


    @Override
    public List<Message> getData() {

        Cursor cursor = mContentResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);

        mMessages = MessageUtil.cursorToList(cursor);

        return offsetMarkers(mMessages);
        //TODO fixOverLAy of messages https://developers.google.com/maps/documentation/android-api/utility/marker-clustering
    }

    @Override
    public int getPosition(Message message) {
        return mMessages.indexOf(message);
    }

    //TODO Refactor this nested loop or make a listView for clustered items
    private List<Message> offsetMarkers(List<Message> messages) {
        double OFFSET = 0.000009;
        for (int i = 0; i <= messages.size() - 1; i++) {
            Random random = new Random();
            //Get one message
            Message message = messages.get(i);
            double lat = Double.parseDouble(message.getLat());
            double lon = Double.parseDouble(message.getLon());
            Location pos = new Location("");
            pos.setLatitude(lat);
            pos.setLongitude(lon);
            for (int j = 0; j <= messages.size() - 1; j++) {
                int randInt = random.nextInt(3);
                //Check Every other message
                Message message1 = messages.get(j);
                double lat1 = Double.parseDouble(message1.getLat());
                double lon1 = Double.parseDouble(message1.getLon());
                Location pos1 = new Location("");
                pos1.setLatitude(lat1);
                pos1.setLongitude(lon1);
                if (pos.distanceTo(pos1) > 0.000005)
                    if (randInt == 0) {
                        message1.setLat(Double.toString(lat1 + OFFSET));
                        message1.setLon(Double.toString(lon1 + OFFSET));
                    } else if (randInt == 1) {
                        message1.setLat(Double.toString(lat1 - OFFSET));
                        message1.setLon(Double.toString(lon1 + OFFSET));
                    } else if (randInt == 2) {
                        message1.setLat(Double.toString(lat1 - OFFSET));
                        message1.setLon(Double.toString(lon1 - OFFSET));
                    } else {
                        message1.setLat(Double.toString(lat1 + OFFSET));
                        message1.setLon(Double.toString(lon1 + OFFSET));
                    }

            }
        }
        return messages;
    }

}