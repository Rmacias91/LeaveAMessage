package com.example.richie.leaveamessage.main.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.Date;
import java.util.List;

/**
 * Created by Richie on 3/23/2018.
 */

public class Message implements ClusterItem {
    private int id;
    private String title;
    private String Message;
    private String distance;
    private String Lat;
    private String Lon;
    @SerializedName("Date")
    private String date;


    //TODO Implement a Date generator
    //TODO Remove this constructor and distance. No need
    public Message(String message, String distance){
        this.Message = message;
        this.distance = distance;
    }
    //TODO Fix constructor to have a date param
     public Message(String title, String message,String lat, String lon){
        this.title = title;
        this.Message = message;
        this.Lat = lat;
        this.Lon = lon;
        this.date = "2018-04-10";
     }

     public Message(String title, int id, double lat, double lon, String date, String message){
         this.title = title;
         this.id=id;
         this.Lat = Double.toString(lat);
         this.Lon = Double.toString(lon);
         this.date = date;
         this.Message = message;
     }


    @Override
    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(Lat),Double.parseDouble(Lon));
    }

    public String getTitle(){
         return title;
    }

    @Override
    public String getSnippet() {
        return Message;
    }

    public String getMessage() {
        return Message;
    }
//TODO Implement a function to calculate Distance. Every time get distance is called?
    public String getDistance() {
        return distance;
    }


    public String getLat(){return Lat;}

    public String getLon(){return Lon;}

    public int getId(){return id;}

    public String getDate() {
        return date;
    }

    public void setMessage(String message){this.Message = message;}
}
