package com.example.richie.leaveamessage.main.UI.MessageList;

import android.content.ContentResolver;
import android.database.Cursor;

import com.example.richie.leaveamessage.main.Util.MessageUtil;
import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 3/23/2018.
 */

class ListViewPresenter implements ListViewContract.PresenterList{
    ListViewContract.ViewList mViewList;
    private ContentResolver mContentResolver;

    public ListViewPresenter(ListViewContract.ViewList viewList, ContentResolver contentResolver){
        mViewList = viewList;
        mContentResolver = contentResolver;
    }

    @Override
    public void getData() {
        Cursor cursor = mContentResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);

        mViewList.setData(MessageUtil.cursorToList(cursor));
    }


}
