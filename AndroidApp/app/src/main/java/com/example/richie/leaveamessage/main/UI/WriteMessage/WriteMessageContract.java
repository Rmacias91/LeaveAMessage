package com.example.richie.leaveamessage.main.UI.WriteMessage;

import com.example.richie.leaveamessage.main.models.Message;

/**
 * Created by Richie on 5/4/2018.
 */

public interface WriteMessageContract {

    public interface presenter{
        void saveMessage(Message message);
    }

    public interface view{
        void showMessage(String message);
    }

}
