package com.example.dan14z.droidbountyhunterbindservice.data;

/**
 * Created by Dan14z on 11/09/2017.
 */

public class DBProvider {
    private static final String LOG_TAG = DBProvider.class.getSimpleName();

    public static final String DATABASE_NAME = "db.bountyhunterbind";

    public static final int DATABASE_VERSION = 1;


    public static class FugitivoEntry{
        public static final String TABLE_NAME = "fugitivos";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_PHOTO = "photo";

        public static final String CREATE_TABLE_FUGITIVOS = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                COLUMN_NAME_PHOTO + " TEXT, " +
                COLUMN_NAME_STATUS + " INTEGER, " +
                "UNIQUE (" + COLUMN_NAME_NAME + ") ON CONFLICT REPLACE);";

        public static final String DROP_IF_EXISTS = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
