package app.warinator.basicmvp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import app.warinator.basicmvp.data.db.DbContract;
import app.warinator.basicmvp.ui.adapter.ShowsCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 0;
    private RecyclerView rvShows;
    private ShowsCursorAdapter showsCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvShows = (RecyclerView) findViewById(R.id.rv_shows);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvShows.setLayoutManager(layoutManager);

        showsCursorAdapter = new ShowsCursorAdapter(this);
        rvShows.setAdapter(showsCursorAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int) viewHolder.itemView.getTag();
                getContentResolver().delete(DbContract.ShowEntry.CONTENT_URI,
                        DbContract.ShowEntry._ID+" = ?", new String[]{String.valueOf(id)} );

                getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);

            }
        }).attachToRecyclerView(rvShows);

        rvShows.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void dummyAdd(){
        ContentValues cv = new ContentValues();
        cv.put(DbContract.ShowEntry.COLUMN_NAME, "Человек-паук");
        cv.put(DbContract.ShowEntry.COLUMN_ORIGINAL_NAME, "Spider man");
        cv.put(DbContract.ShowEntry.COLUMN_OVERVIEW, "Человек получил способности паука. Свежо. Оригинально.");
        Uri uri = getContentResolver().insert(DbContract.ShowEntry.CONTENT_URI, cv);

        if (uri != null){
            Toast.makeText(this, "Yay, this thing is added! as "+ uri,Toast.LENGTH_SHORT).show();
        }

        cv = new ContentValues();
        cv.put(DbContract.ShowEntry.COLUMN_NAME, "Паук-человек");
        cv.put(DbContract.ShowEntry.COLUMN_ORIGINAL_NAME, "Man spider");
        cv.put(DbContract.ShowEntry.COLUMN_OVERVIEW, "Паук получил человечьи способности. Вот это поворот!");
        uri = getContentResolver().insert(DbContract.ShowEntry.CONTENT_URI, cv);

        if (uri != null){
            Toast.makeText(this, "Yay, this thing is added! as "+ uri,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor showsData = null;

            @Override
            protected void onStartLoading() {
                if (showsData != null){
                    deliverResult(showsData);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(DbContract.ShowEntry.CONTENT_URI,
                            null, null, null, null);
                }
                catch (Exception e){
                    Log.e(getClass().getSimpleName(), "Failed to load shows");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data){
                showsData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        showsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        showsCursorAdapter.swapCursor(null);
    }
}
