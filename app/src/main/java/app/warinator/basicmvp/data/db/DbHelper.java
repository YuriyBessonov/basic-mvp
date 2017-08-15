package app.warinator.basicmvp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static app.warinator.basicmvp.data.db.DbContract.ShowEntry.COLUMN_NAME;
import static app.warinator.basicmvp.data.db.DbContract.ShowEntry.COLUMN_ORIGINAL_NAME;
import static app.warinator.basicmvp.data.db.DbContract.ShowEntry.COLUMN_OVERVIEW;
import static app.warinator.basicmvp.data.db.DbContract.ShowEntry.COLUMN_POSTER_PATH;
import static app.warinator.basicmvp.data.db.DbContract.ShowEntry.COLUMN_VOTE_AVG;

/**
 * Database helper
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shows.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addShowsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void addShowsTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DbContract.ShowEntry.TABLE_NAME + " (" +
                        DbContract.ShowEntry._ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_ORIGINAL_NAME + " TEXT NOT NULL, " +
                        COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                        COLUMN_VOTE_AVG + " REAL, " +
                        COLUMN_POSTER_PATH + " TEXT " +
                        ");"
        );
    }
}
