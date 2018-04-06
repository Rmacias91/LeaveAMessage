package com.example.richie.leaveamessage.main.UI.ReadMessage;

import com.example.richie.leaveamessage.main.Model.Message;

import java.util.List;

/**
 * Created by Richie on 4/5/2018.
 */

public interface ReadMessageContract {

    public interface ReadMessageView{

    }

    public interface ReadMessagePresenter {

        public List<Message> getData();

        public Message getOneMessage(int position);

    }

    public interface ReadMessageModel{

    }
}
