package app.warinator.basicmvp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import app.warinator.basicmvp.R;
import app.warinator.basicmvp.data.db.DbContract;
import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.data.network.RetrofitHelper;
import app.warinator.basicmvp.data.network.model.TvShowDetails;
import app.warinator.basicmvp.data.network.model.TvShowsPage;
import app.warinator.basicmvp.utils.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Data manager class
 */

public class DataManager implements IDataManager {

    private static DataManager instance;
    private Context context;


    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager();
            instance.context = context.getApplicationContext();
        }
        return instance;
    }

    @Override
    public Observable<List<TvShow>> getShows() {
        if (NetworkUtils.isConnectedToNetwork(context)) {
            return RetrofitHelper.getShowsAPI()
                    .getShowsOnTheAir(context.getString(R.string.themoviedb_api_key),
                            Locale.getDefault().getLanguage(), 1)
                    .observeOn(Schedulers.io())
                    .map(new Function<TvShowsPage, List<TvShow>>() {
                        @Override
                        public List<TvShow> apply(@NonNull TvShowsPage showsPage) throws Exception {
                            List<TvShow> retShows = new ArrayList<>(showsPage.getTvShows().size());
                            for (int i = 0; i < showsPage.getTvShows().size(); i++) {
                                app.warinator.basicmvp.data.network.model.TvShow show =
                                        showsPage.getTvShows().get(i);
                                retShows.add(new TvShow(show.getId(), show.getName(),
                                        show.getOriginalName(), show.getOverview(),
                                        show.getVoteAverage(), show.getPosterPath(),
                                        getShowRating(show.getId())));
                            }
                            Collections.sort(retShows, new Comparator<TvShow>() {
                                @Override
                                public int compare(TvShow s1, TvShow s2) {
                                    float r1 = s1.getVoteAverage();
                                    float r2 = s2.getVoteAverage();
                                    if (r1 != r2) {
                                        return r1 < r2 ? 1 : -1;
                                    }
                                    return 0;
                                }
                            });
                            return retShows;
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        } else {
            return Observable.fromCallable(new Callable<List<TvShow>>() {
                @Override
                public List<TvShow> call() throws Exception {
                    Uri uri = DbContract.ShowEntry.CONTENT_URI;
                    Cursor cursor = context.getContentResolver().query(uri, null, null, null,
                            DbContract.ShowEntry.COLUMN_VOTE_AVG + " DESC");
                    List<TvShow> shows = new ArrayList<>();
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            shows.add(TvShow.map(cursor));
                        }
                    }
                    return shows;
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }

    @Override
    public Observable<TvShow> getShow(final int id) {
        if (NetworkUtils.isConnectedToNetwork(context)) {
            return RetrofitHelper.getShowsAPI()
                    .getSpecificShow(id, context.getString(R.string.themoviedb_api_key),
                            Locale.getDefault().getLanguage())
                    .observeOn(Schedulers.io())
                    .map(new Function<TvShowDetails, TvShow>() {
                        @Override
                        public TvShow apply(@NonNull TvShowDetails show) throws Exception {
                            return new TvShow(show.getId(), show.getName(),
                                    show.getOriginalName(), show.getOverview(),
                                    show.getVoteAverage(), show.getPosterPath(),
                                    getShowRating(show.getId()));
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        } else {
            return Observable.fromCallable(new Callable<TvShow>() {
                @Override
                public TvShow call() throws Exception {
                    Uri uri = DbContract.ShowEntry.buildShowUri(id);
                    Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                    TvShow retShow = null;
                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        retShow = TvShow.map(cursor);
                        cursor.close();
                    }
                    return retShow;
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }

    @Override
    public Observable<Integer> rateShow(final int id, final float rating) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Uri uri = DbContract.ShowEntry.CONTENT_URI;
                ContentValues cv = new ContentValues();
                cv.put(DbContract.ShowEntry.COLUMN_USER_RATING, rating);
                return context.getContentResolver().update(uri, cv, DbContract.ShowEntry._ID + " = ?",
                        new String[]{String.valueOf(id)});
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Integer> replaceShows(final List<TvShow> newShows) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Uri uri = DbContract.ShowEntry.CONTENT_URI;
                context.getContentResolver().delete(uri, null, null);
                for (TvShow show : newShows) {
                    context.getContentResolver().insert(uri, show.getContentValues());
                }
                return newShows.size();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private float getShowRating(int showId) {
        Uri uri = DbContract.ShowEntry.buildShowUri(showId);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        float rating;
        if (cursor == null || cursor.getCount() < 1) {
            return 0;
        } else {
            cursor.moveToFirst();
            rating = cursor.getFloat(cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_USER_RATING));
            cursor.close();
        }
        return rating;
    }

}
