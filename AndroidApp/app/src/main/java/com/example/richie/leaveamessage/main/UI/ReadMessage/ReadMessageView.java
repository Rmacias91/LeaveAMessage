package com.example.richie.leaveamessage.main.UI.ReadMessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.richie.leaveamessage.R;
import com.example.richie.leaveamessage.main.models.Message;

/**
 * Created by Richie on 4/3/2018.
 */

public class ReadMessageView extends FragmentActivity implements
        ReadMessageContract.ReadMessageView{

    public static final String FRAGMENT_TITLE_KEY = "title";
    public static final String FRAGMENT_MESSAGE_KEY = "message";
    public static final String POSITION_EXTRA= "position_extra";


    private ViewPager mPager;
    private PagerAdapter mPageAdapter;
    private ReadMessageContract.ReadMessagePresenter mPresenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_activity_layout);
        Intent intent = getIntent();
        int start_position = intent.getIntExtra(POSITION_EXTRA,0);
        mPresenter = new ReadMessagePresenter(this,getContentResolver());
        mPager = findViewById(R.id.pager);
        mPageAdapter = new ScreenSlidePageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);
        mPager.setCurrentItem(start_position);

    }

    private class ScreenSlidePageAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePageAdapter(FragmentManager supportFragmentManager){
            super(supportFragmentManager);
        }


        @Override
        public Fragment getItem(int position) {
            Message message = mPresenter.getOneMessage(position);
            return ReadMessageFragment.newInstance(
                    message.getTitle(),message.getMessage());
        }

        @Override
        public int getCount() {
            return mPresenter.getData().size();
        }
    }
}
