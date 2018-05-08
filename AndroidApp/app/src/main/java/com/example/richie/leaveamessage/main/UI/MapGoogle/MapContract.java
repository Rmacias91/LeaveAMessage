package com.example.richie.leaveamessage.main.UI.MapGoogle;

import com.example.richie.leaveamessage.main.models.Message;

import java.util.List;

/**
 * Created by Richie on 4/3/2018.
 */

public interface MapContract {

    interface ViewMap{
    }

    interface PresenterMap{
        List<Message> getData();
        int getPosition(Message message);
    }

    interface ModelMap{

    }


}
