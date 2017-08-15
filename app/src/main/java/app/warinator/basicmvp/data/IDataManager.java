package app.warinator.basicmvp.data;

import java.util.List;

import app.warinator.basicmvp.data.db.model.TvShow;
import io.reactivex.Observable;

/**
 * Data manager interface
 */

public interface IDataManager {
    Observable<List<TvShow>> getShows();
    Observable<TvShow> getShow(int id);
}
