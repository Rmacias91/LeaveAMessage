package com.example.richie.leaveamessage.main.UI.ReadMessage;

import com.example.richie.leaveamessage.main.models.Message;

import java.util.List;

/**
 * Created by Richie on 4/5/2018.
 */

public interface ReadMessageContract {

    public interface ReadMessageView{

        public void showMessage(String message);

        public void finishRead();

    }

    public interface ReadMessagePresenter {

        public List<Message> getData();

        public Message getOneMessage(int position);

        public void deleteMessage();

    }

    public interface ReadMessageModel{

    }
}
