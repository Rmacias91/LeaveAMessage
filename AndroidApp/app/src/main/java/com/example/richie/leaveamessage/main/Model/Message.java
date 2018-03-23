package com.example.richie.leaveamessage.main.Model;

import java.util.Date;

/**
 * Created by Richie on 3/23/2018.
 */

public class Message {
    private String message;
    private String distance;
    private Date date;

    //TODO Implement a Date generator

    public Message(String message, String distance){
        this.message = message;
        this.distance = distance;
    }

    public String getMessage() {
        return message;
    }
//TODO Implement a function to calculate Distance. Every time get distance is called?
    public String getDistance() {
        return distance;
    }

    public Date getDate() {
        return date;
    }


}
