package com.example.richie.leaveamessage.main.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.richie.leaveamessage.main.data.MessageContract.MessageEntry;

/**
 * Created by Richie on 4/30/2018.
 */

public class MessageDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";

    private static final int DATABASE_VERSION = 1;

    public MessageDbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    //TODO Probably add Unique Constraints to avoid duplicates
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MESSAGE_TABLE =
                "CREATE TABLE " + MessageEntry.TABLE_NAME + "(" +
                        MessageEntry.COLUMN_ID    +" INTEGER PRIMARY KEY," +
                        MessageEntry.COLUMN_LAT + " FLOAT NOT NULL," +
                        MessageEntry.COLUMN_LON + " FLOAT NOT NULL," +
                        MessageEntry.COLUMN_MESSAGE + "VARCHAR NOT NULL," +
                        MessageEntry.COLUMN_DATE + "DATE NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
