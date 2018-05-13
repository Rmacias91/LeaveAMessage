package com.example.richie.leaveamessage.main.UI.ReadMessage;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.richie.leaveamessage.R;

/**
 * Created by Richie on 4/5/2018.
 */

public class ReadMessageFragment extends Fragment {
    private TextView titleTV;
    private TextView messageTV;
    private String mTitle;
    private String mMessage;
    private static final String TAG = ReadMessageFragment.class.getSimpleName();

    public static ReadMessageFragment newInstance(String title, String message){
        ReadMessageFragment fragment = new ReadMessageFragment();
        Bundle args = new Bundle();
        args.putString(ReadMessageView.FRAGMENT_TITLE_KEY,title);
        args.putString(ReadMessageView.FRAGMENT_MESSAGE_KEY,message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ReadMessageView.FRAGMENT_TITLE_KEY,"");
        mMessage = getArguments().getString(ReadMessageView.FRAGMENT_MESSAGE_KEY,"");
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        Log.d(TAG,"Fragment Options Menu Created");
//        inflater.inflate(R.menu.menu_setting_read, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.item_delete:
//                ReadMessageView.mPresenter.deleteMessage();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.read_fragement_layout,container,false);
        setHasOptionsMenu(true);
        titleTV = view.findViewById(R.id.title_frag_text);
        messageTV = view.findViewById(R.id.message_frag_text);
        titleTV.setText(mTitle);
        messageTV.setText(mMessage);
        return view;
    }


}
