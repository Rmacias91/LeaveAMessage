package com.example.richie.leaveamessage.main.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Richie on 4/30/2018.
 */

public class MessageDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db"

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
