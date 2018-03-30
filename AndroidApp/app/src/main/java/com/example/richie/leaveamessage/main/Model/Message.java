package com.example.richie.leaveamessage.main.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Richie on 3/23/2018.
 */

public class Message {
    private String title;
    private String message;
    private String distance;
    private String lat;
    private String lon;
    private Date date;

    //TODO Implement a Date generator
    //TODO Remove this constructor and distance. No need
    public Message(String message, String distance){
        this.message = message;
        this.distance = distance;
    }
     public Message(String title, String message,String lat, String lon){
        this.title = title;
        this.message = message;
        this.lat = lat;
        this.lon = lon;
     }


    public String getTitle(){
         return title;
    }
    public String getMessage() {
        return message;
    }
//TODO Implement a function to calculate Distance. Every time get distance is called?
    public String getDistance() {
        return distance;
    }

    public LatLng getLatLng(){
        return new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
    }

    public Date getDate() {
        return date;
    }


}
