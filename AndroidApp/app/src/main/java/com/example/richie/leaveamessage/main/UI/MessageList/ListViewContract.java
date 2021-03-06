package com.example.richie.leaveamessage.main.UI.MessageList;

import com.example.richie.leaveamessage.main.models.Message;

import java.util.List;

/**
 * Created by Richie on 3/22/2018.
 */

public interface ListViewContract {

    interface ViewList{
        void setData(List<Message> messages);
    }

    interface PresenterList{

         void getData();
    }

    interface ModelList{

    }
}
