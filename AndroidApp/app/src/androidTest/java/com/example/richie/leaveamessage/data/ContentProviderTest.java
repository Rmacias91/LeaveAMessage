package com.example.richie.leaveamessage.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.example.richie.leaveamessage.main.data.MessageContract;
import com.example.richie.leaveamessage.main.data.MessageProvider;




/**
 * Created by Richie on 5/1/2018.
 */

//@RunWith(AndroidJUnit4.class)
public class ContentProviderTest extends ProviderTestCase2<MessageProvider>{

    MockContentResolver mMockResolver;
    public ContentProviderTest() {
        super(MessageProvider.class, "com.example.richie.leaveamessage");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setContext(InstrumentationRegistry.getTargetContext());
        mMockResolver = getMockContentResolver();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //Test Single Insert and Query
    public void testMessageInsert(){
        Uri uri = MessageContract.MessageEntry.CONTENT_URI.buildUpon().appendPath("1").build();
        uri = mMockResolver.insert(uri,
                getMessageContentValues());
        Cursor cursor = mMockResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals(VALID_ID,cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_ID)));
        assertEquals(VALID_LAT,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LAT)));
        assertEquals(VALID_LON,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LON)));
        assertEquals(VALID_DATE,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_DATE)));
        assertEquals(VALID_MESSAGE,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_MESSAGE)));
    }

    //Test Bulk Insert and Query
    public void testBulkInsert(){
        Uri uri = MessageContract.MessageEntry.CONTENT_URI;
        int rowsInserted = mMockResolver.bulkInsert(uri,
                getMultipleMessagesCV());
        Cursor cursor = mMockResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);
        assertNotNull(cursor);
        assertEquals(2, cursor.getCount());
        assertEquals(2, rowsInserted);
        assertTrue(cursor.moveToFirst());
        assertEquals(VALID_ID_2,cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_ID)));
        assertEquals(VALID_LAT,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LAT)));
        assertEquals(VALID_LON,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LON)));
        assertEquals(VALID_DATE,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_DATE)));
        assertEquals(VALID_MESSAGE_2,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_MESSAGE)));
        cursor.moveToNext();
        assertEquals(VALID_ID_3,cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_ID)));
        assertEquals(VALID_LAT,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LAT)));
        assertEquals(VALID_LON,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LON)));
        assertEquals(VALID_DATE,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_DATE)));
        assertEquals(VALID_MESSAGE_3,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_MESSAGE)));
    }

    public void testDelete(){
        Uri uri = MessageContract.MessageEntry.CONTENT_URI;
        int rowsInserted = mMockResolver.bulkInsert(uri,
                getMultipleMessagesCV());
        int rowsdeleted = mMockResolver.delete(uri.buildUpon().appendPath("2").build(),null,null);
        assertEquals(1,rowsdeleted);
        Cursor cursor = mMockResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        assertEquals(2, rowsInserted);
        assertTrue(cursor.moveToFirst());
        assertEquals(VALID_ID_3,cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_ID)));
        assertEquals(VALID_LAT,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LAT)));
        assertEquals(VALID_LON,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LON)));
        assertEquals(VALID_DATE,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_DATE)));
        assertEquals(VALID_MESSAGE_3,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_MESSAGE)));
    //I can test for delete all, but I prob won't need to ever use.
    }

    public void testUpdate(){

        Uri uri = MessageContract.MessageEntry.CONTENT_URI.buildUpon().appendPath("1").build();
        uri = mMockResolver.insert(uri,
                getMessageContentValues());

        int taskUpdated = mMockResolver.update(uri,getMessageContentValues2(),null,null);
        Cursor cursor = mMockResolver.query(MessageContract.MessageEntry.CONTENT_URI,
                null,
                null,
                new String[]{},
                null);
        assertEquals(1,taskUpdated);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals(VALID_ID_2,cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_ID)));
        assertEquals(VALID_LAT,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LAT)));
        assertEquals(VALID_LON,cursor.getDouble(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_LON)));
        assertEquals(VALID_DATE,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_DATE)));
        assertEquals(VALID_MESSAGE_2,cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.COLUMN_MESSAGE)));

    }



    public static ContentValues getMessageContentValues(){

        ContentValues values = new ContentValues();
        values.put(MessageContract.MessageEntry.COLUMN_ID,VALID_ID);
        values.put(MessageContract.MessageEntry.COLUMN_LAT,VALID_LAT);
        values.put(MessageContract.MessageEntry.COLUMN_LON,VALID_LON);
        values.put(MessageContract.MessageEntry.COLUMN_DATE,VALID_DATE);
        values.put(MessageContract.MessageEntry.COLUMN_MESSAGE,VALID_MESSAGE);

        return values;
    }

    public static ContentValues getMessageContentValues2(){

        ContentValues values = new ContentValues();
        values.put(MessageContract.MessageEntry.COLUMN_ID,VALID_ID_2);
        values.put(MessageContract.MessageEntry.COLUMN_LAT,VALID_LAT);
        values.put(MessageContract.MessageEntry.COLUMN_LON,VALID_LON);
        values.put(MessageContract.MessageEntry.COLUMN_DATE,VALID_DATE);
        values.put(MessageContract.MessageEntry.COLUMN_MESSAGE,VALID_MESSAGE_2);

        return values;
    }

    public static ContentValues getMessageContentValues3(){

        ContentValues values = new ContentValues();
        values.put(MessageContract.MessageEntry.COLUMN_ID,VALID_ID_3);
        values.put(MessageContract.MessageEntry.COLUMN_LAT,VALID_LAT);
        values.put(MessageContract.MessageEntry.COLUMN_LON,VALID_LON);
        values.put(MessageContract.MessageEntry.COLUMN_DATE,VALID_DATE);
        values.put(MessageContract.MessageEntry.COLUMN_MESSAGE,VALID_MESSAGE_3);

        return values;
    }

    public static ContentValues[] getMultipleMessagesCV(){
        ContentValues cv2 = getMessageContentValues2();
        ContentValues cv3 = getMessageContentValues3();
        ContentValues[] cv= {cv2,cv3};
        return cv;
    }

    //Refactor to arrays of ids,lats, lon, etc and define a function to create an array of CVS in a loop.
    private static final int VALID_ID        = 1;
    private static final double VALID_LAT     = 41.9456354;
    private static final double VALID_LON    = -87.6679754;
    private static final String VALID_DATE    = "2018-05-01'T'12:12:32Z";
    private static final String VALID_MESSAGE = "Testing my Content Provider";

    private static final int VALID_ID_2        = 2;
    private static final String VALID_MESSAGE_2 = "Testing my Bulk Insert";

    private static final int VALID_ID_3        = 3;
    private static final String VALID_MESSAGE_3 = "Testing my Bulk Insert twice";


}