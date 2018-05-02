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

    public static ContentValues getMessageContentValues(){

        ContentValues values = new ContentValues();
        values.put(MessageContract.MessageEntry.COLUMN_ID,VALID_ID);
        values.put(MessageContract.MessageEntry.COLUMN_LAT,VALID_LAT);
        values.put(MessageContract.MessageEntry.COLUMN_LON,VALID_LON);
        values.put(MessageContract.MessageEntry.COLUMN_DATE,VALID_DATE);
        values.put(MessageContract.MessageEntry.COLUMN_MESSAGE,VALID_MESSAGE);

        return values;
    }

    private static final int VALID_ID        = 1;
    private static final double VALID_LAT     = 41.9456354;
    private static final double VALID_LON    = -87.6679754;
    private static final String VALID_DATE    = "2018-05-01'T'12:12:32Z";

    private static final String VALID_MESSAGE = "Testing my Content Provider";


}
