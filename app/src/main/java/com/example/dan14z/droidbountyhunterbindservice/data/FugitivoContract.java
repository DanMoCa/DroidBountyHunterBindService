package com.example.dan14z.droidbountyhunterbindservice.data;

import android.net.Uri;

/**
 * Created by Dan14z on 10/09/2017.
 */

public class FugitivoContract {
    public static final String CONTENT_AUTHORITY = "training.edu.droidbountyhunter.fugitivos";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FUGITIVOS = "fugitivos";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_FUGITIVOS);
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_NAME = "name";

    public static final String COLUMN_NAME_PHOTO = "photo";
}
