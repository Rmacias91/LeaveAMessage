package com.example.richie.leaveamessage.main.UI.MapGoogle;


import com.example.richie.leaveamessage.main.Model.Message;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 4/3/2018.
 */

public class MapPresenter implements MapContract.PresenterMap {
    private MapContract.ViewMap view;


    MapPresenter(MapContract.ViewMap view){

    }


    @Override
    public List<Message> getData() {

        //Need 7 decimals
        //TODO fixOverLAy of messages https://developers.google.com/maps/documentation/android-api/utility/marker-clustering
        List<Message> mTestMessageList = new ArrayList<Message>() {{
            add(new Message("Look here", "Made ya Look", "41.9456354", "-87.6679754"));
            add(new Message("Eyyyyy", "You made it to the coolest block in the city!", "42.9525643", "-87.6542044"));
            add(new Message("Free Dominos", "Yum pizza", "41.9525644", "-87.6542000"));
            add(new Message("I grew up here", "Hope you guys enjoy house! Great Memories", "41.9456359", "-87.6679759"));
        }};

        return mTestMessageList;
    }

}