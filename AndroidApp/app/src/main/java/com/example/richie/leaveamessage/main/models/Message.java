package com.example.richie.leaveamessage.main.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by Richie on 3/23/2018.
 */

public class Message {
    private Boolean success;
    private int id;
    private String title;
    private String Message;
    private String distance;
    private String Lat;
    private String Lon;
    private String date;
    private List<Message> messages;

    //TODO Implement a Date generator
    //TODO Remove this constructor and distance. No need
    public Message(String message, String distance){
        this.Message = message;
        this.distance = distance;
    }
     public Message(String title, String message,String lat, String lon){
        this.title = title;
        this.Message = message;
        this.Lat = lat;
        this.Lon = lon;
     }

//        DateFormat format = new SimpleDateFormat("YYYY-MM-DDTHH:mm:ss.sssZ", Locale.ENGLISH);
//        try {
//            this.date = format.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }



    public String getTitle(){
         return title;
    }
    public String getMessage() {
        return Message;
    }
//TODO Implement a function to calculate Distance. Every time get distance is called?
    public String getDistance() {
        return distance;
    }

    public LatLng getLatLng(){return new LatLng(Double.parseDouble(Lat),Double.parseDouble(Lon));}

    public String getLat(){return Lat;}

    public String getLon(){return Lon;}

    public int getId(){return id;}

    public String getDate() {
        return date;
    }


    public List<com.example.richie.leaveamessage.main.models.Message> getMessages() {
        return messages;
    }

    public Boolean getSuccess() {
        return success;
    }
}
