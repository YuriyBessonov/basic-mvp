package app.warinator.basicmvp.data.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Database contract
 */

public class DbContract {
    public static final String AUTHORITY = "app.warinator.basicmvp";
    public static final String PATH_SHOW = "show";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class ShowEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SHOW).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_SHOW;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_SHOW;

        public static final String TABLE_NAME = "shows";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ORIGINAL_NAME = "original_name";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVG = "vote_avg";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_USER_RATING = "user_rating";

        public static Uri buildShowUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
