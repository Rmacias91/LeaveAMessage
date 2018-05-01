package com.example.richie.leaveamessage.main.data;

import android.content.ContentProvider;
import android.content.ContentUris;
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
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }

                //As Long as Context is passed and not null, Content Resolver will not be null
                if (rowsInserted > 0) getContext().getContentResolver().notifyChange(uri, null);

                return rowsInserted;

            default: return super.bulkInsert(uri,values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch(sUriMatcher.match(uri)){

            case CODE_MESSAGE_WITH_ID:
                String idString = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{idString};
                cursor = mOpenHelper.getReadableDatabase().query(
                        MessageContract.MessageEntry.TABLE_NAME,
                        projection,
                        MessageContract.MessageEntry.COLUMN_ID + " = ?",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_MESSAGE:
                cursor = mOpenHelper.getReadableDatabase().query(
                        MessageContract.MessageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;


            default:
                throw new UnsupportedOperationException("Unknown URI: "+uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        Uri returnUri;
        switch(sUriMatcher.match(uri)){

            case CODE_MESSAGE_WITH_ID:
                long _id = db.insert(MessageContract.MessageEntry.TABLE_NAME,null,contentValues);
                if(_id>0){
                    returnUri = ContentUris.withAppendedId(MessageContract.MessageEntry.CONTENT_URI,_id);

                }else{
                    throw new android.database.SQLException("Failed to insert row into "+ uri);
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);

        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        /* Users of the delete method will expect the number of rows deleted to be returned. */
        int numRowsDeleted;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_MESSAGE:
                numRowsDeleted = db.delete(
                        MessageContract.MessageEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            case CODE_MESSAGE_WITH_ID:
                String idString = uri.getLastPathSegment();
                numRowsDeleted = db.delete(MessageContract.MessageEntry.TABLE_NAME, "_id=?", new String[]{idString});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
