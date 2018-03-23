package com.example.richie.leaveamessage.main.UI.MessageList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.richie.leaveamessage.R;
import com.example.richie.leaveamessage.main.Model.Message;


import java.util.List;


/**
 * Created by Richie on 3/22/2018.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyViewHolder> {
    private static final String TAG = ListViewAdapter.class.getSimpleName();
    private List<Message> mMessageList;

    public ListViewAdapter(List<Message> messageList){
        this.mMessageList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_in_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView messageText = holder.messageText;
        TextView distanceText = holder.distanceText;
        Message currentMessage = mMessageList.get(position);
        messageText.setText(currentMessage.getMessage());
        distanceText.setText(currentMessage.getDistance());
        Log.d(TAG,"message at pos:"+ position+ " is: "+ currentMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView messageText;
        private TextView distanceText;

        public MyViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_list_item);
            distanceText = itemView.findViewById(R.id.distance_list_item);
        }
    }
}
