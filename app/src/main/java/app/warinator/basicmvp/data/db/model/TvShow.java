package app.warinator.basicmvp.data.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import app.warinator.basicmvp.data.db.DbContract;

public class TvShow {

    private int id;
    private String name;
    private String originalName;
    private String overview;
    private float voteAverage;
    private String posterPath;
    private float userRating;

    public TvShow(int id, String name, String originalName, String overview,
                  float voteAverage, String posterPath, float userRating) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.userRating = userRating;
    }

    public TvShow() {
    }

    public static TvShow map(Cursor cursor) {
        TvShow show = new TvShow();

        int idInd = cursor.getColumnIndex(DbContract.ShowEntry._ID);
        if (idInd >= 0) {
            show.id = cursor.getInt(idInd);
        }

        int nameInd = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_NAME);
        if (nameInd >= 0) {
            show.name = cursor.getString(nameInd);
        }

        int origNameInd = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_ORIGINAL_NAME);
        if (origNameInd >= 0) {
            show.originalName = cursor.getString(origNameInd);
        }

        int overviewInd = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_OVERVIEW);
        if (overviewInd >= 0) {
            show.overview = cursor.getString(overviewInd);
        }

        int voteAvgInd = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_VOTE_AVG);
        if (voteAvgInd >= 0) {
            show.voteAverage = cursor.getFloat(voteAvgInd);
        }

        int posterPathInd = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_POSTER_PATH);
        if (posterPathInd >= 0) {
            show.posterPath = cursor.getString(posterPathInd);
        }

        int userRatingInd = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_USER_RATING);
        if (userRatingInd >= 0) {
            show.userRating = cursor.getFloat(userRatingInd);
        }

        return show;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();

        cv.put(DbContract.ShowEntry._ID, id);
        cv.put(DbContract.ShowEntry.COLUMN_NAME, name);
        cv.put(DbContract.ShowEntry.COLUMN_ORIGINAL_NAME, originalName);
        cv.put(DbContract.ShowEntry.COLUMN_OVERVIEW, overview);
        cv.put(DbContract.ShowEntry.COLUMN_VOTE_AVG, voteAverage);
        cv.put(DbContract.ShowEntry.COLUMN_POSTER_PATH, posterPath);
        cv.put(DbContract.ShowEntry.COLUMN_USER_RATING, userRating);

        return cv;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
