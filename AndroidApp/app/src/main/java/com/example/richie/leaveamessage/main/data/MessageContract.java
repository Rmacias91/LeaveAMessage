package com.example.richie.leaveamessage.main.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Richie on 4/30/2018.
 */

public class MessageContract {
    public static final String CONTENT_AUTHORITY = "com.example.richie.leaveamessage";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MESSAGE = "message";

    public static final class MessageEntry implements BaseColumns {

        public static final String TABLE_NAME = "messages";

        public static final String COLUMN_ID = "message_id";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LON = "lon";
    }
}
