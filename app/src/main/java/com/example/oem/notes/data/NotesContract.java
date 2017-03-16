package com.example.oem.notes.data;

import android.provider.BaseColumns;

public final class NotesContract {
    private NotesContract() {
    };

    public static final class NotesEntry implements BaseColumns {

        public final static String TABLE_NAME = "notes";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_CONTENT = "content";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_PICTURE = "picture";
    }
}
