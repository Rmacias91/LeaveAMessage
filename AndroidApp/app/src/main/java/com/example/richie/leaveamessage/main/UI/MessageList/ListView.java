package com.example.richie.leaveamessage.main.UI.MessageList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.richie.leaveamessage.R;
import com.example.richie.leaveamessage.main.Model.Message;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 3/22/2018.
 */

public class ListView extends AppCompatActivity implements ListViewContract.ViewList,ListViewAdapter.ListItemClickListener {
    RecyclerView mRecyclerView;
    private static final String TAG = ListView.class.getSimpleName();
    private RecyclerView.LayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);
        //Test List
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello","2mi"));
        messages.add(new Message("There","3mi"));
        messages.add(new Message("Richie","4mi"));

        Log.d(TAG,"Messages added are:" + messages.toString() );

        mRecyclerView = findViewById(R.id.recycle_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListViewAdapter(this,messages);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG,"CLICKED!");
        Toast.makeText(this,"Starting activity to read message at pos" + clickedItemIndex,Toast.LENGTH_SHORT).show();
    }
}
