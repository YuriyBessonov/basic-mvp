package app.warinator.basicmvp.data;

import java.util.List;

import app.warinator.basicmvp.data.db.model.TvShow;
import io.reactivex.Observable;

/**
 * Data manager class
 */

public class DataManager implements IDataManager {

    @Override
    public Observable<List<TvShow>> getShows() {
        return null;
    }

    @Override
    public Observable<TvShow> getShow(int id) {
        return null;
    }
}
