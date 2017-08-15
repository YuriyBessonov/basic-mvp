package app.warinator.basicmvp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import app.warinator.basicmvp.data.db.DbContract;
import app.warinator.basicmvp.data.db.DbHelper;

/**
 * Content provider
 */

public class ShowsProvider extends ContentProvider {

    public static final int SHOW = 100;
    public static final int SHOW_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private DbHelper dbHelper;

    public static UriMatcher buildUriMatcher(){
        String content = DbContract.AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, DbContract.PATH_SHOW, SHOW);
        matcher.addURI(content, DbContract.PATH_SHOW + "/#", SHOW_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor retCursor;
        switch (uriMatcher.match(uri)){
            case SHOW:
                retCursor = db.query(
                        DbContract.ShowEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SHOW_ID:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        DbContract.ShowEntry.TABLE_NAME,
                        projection,
                        DbContract.ShowEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw newUnknownUriEx(uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case SHOW:
                return DbContract.ShowEntry.CONTENT_TYPE;
            case SHOW_ID:
                return DbContract.ShowEntry.CONTENT_ITEM_TYPE;
            default:
                throw newUnknownUriEx(uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        long _id;
        Uri retUri;

        switch (uriMatcher.match(uri)){
            case SHOW:
                _id = db.insert(DbContract.ShowEntry.TABLE_NAME, null, contentValues);
                if (_id > 0){
                    retUri = DbContract.ShowEntry.buildShowUri(_id);
                }
                else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw newUnknownUriEx(uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)){
            case SHOW:
                rows = db.delete(DbContract.ShowEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw newUnknownUriEx(uri);
        }
        if (selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)){
            case SHOW:
                rows = db.update(DbContract.ShowEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            default:
                throw newUnknownUriEx(uri);
        }

        if (rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    private RuntimeException newUnknownUriEx(Uri uri) {
       return new UnsupportedOperationException("Unknown uri: " + uri);
    }
}
