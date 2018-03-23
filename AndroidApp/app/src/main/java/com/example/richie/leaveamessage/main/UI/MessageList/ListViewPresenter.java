package com.example.richie.leaveamessage.main.UI.MessageList;

import com.example.richie.leaveamessage.main.Model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 3/23/2018.
 */

class ListViewPresenter implements ListViewContract.PresenterList{
    ListViewContract.ViewList mViewList;
    public ListViewPresenter(ListViewContract.ViewList viewList){
        mViewList = viewList;
    }

    @Override
    public List<Message> getData() {
        //Grab Data From Model
        //Test List
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello","2mi"));
        messages.add(new Message("There","3mi"));
        messages.add(new Message("Richie","4mi"));
        mViewList.setData(messages);
        return null;
    }


}
