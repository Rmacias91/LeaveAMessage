package com.example.richie.leaveamessage.main.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Richie on 4/30/2018.
 */

public class MessageProvider extends ContentProvider {

    public static final int CODE_MESSAGE = 100;
    public static final int CODE_MESSAGE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MessageDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MessageContract.CONTENT_AUTHORITY;

        /*
         * For each type of URI you want to add, create a corresponding code. Preferably, these are
         * constant fields in your class so that you can use them throughout the class and you no
         * they aren't going to change. In Sunshine, we use CODE_WEATHER or CODE_WEATHER_WITH_DATE.
         */

        /* This URI is content://com.example.android.sunshine/weather/ */
        matcher.addURI(authority, MessageContract.PATH_MESSAGE, CODE_MESSAGE);

        /*
         * This URI would look something like content://com.example.com.example.richie.leaveamessage/message/12
         * The "/#" signifies to the UriMatcher that if PATH_MESSAGE is followed by ANY number,
         * that it should return the CODE_MESSAGE_WITH_ID code
         */
        matcher.addURI(authority, MessageContract.PATH_MESSAGE + "/#", CODE_MESSAGE_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MessageDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch(sUriMatcher.match(uri)){
            case CODE_MESSAGE:
                db.beginTransaction();
                int rowsInserted = 0;
                try{
                    for(ContentValues value : values) {
                        long _id = db.insert(MessageContract.MessageEntry.TABLE_NAME,null,value);
                        if(_id != -1){
                            rowsInserted++;
                        }
                    }

                }finally {

                }
                default: return super.bulkInsert(uri,values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
