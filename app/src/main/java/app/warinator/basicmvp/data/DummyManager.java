package app.warinator.basicmvp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.warinator.basicmvp.data.db.model.TvShow;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Dummy data manager
 */

public class DummyManager implements IDataManager {

    List<TvShow> shows;

    public DummyManager() {
        shows = new ArrayList<>(5);
        shows.add(new TvShow(0, "Человек паук", "Spider man", "Человек стал пауком", 6.7f, null));
        shows.add(new TvShow(1, "Паук человек", "Man spider", "Паук стал человеком", 7.6f, null));
        shows.add(new TvShow(2, "Гадкий я 3", "Despicable me 3", "Опять этот лысый", 6.1f, null));
        shows.add(new TvShow(3, "Эмоджи фильм", "Emoji film", "Представьте, что кто-то решил снять " +
                "самый нелепый и неинтересный мультфильм. Постойте, вам даже не требуется представлять, " +
                "он уже выходит на экраны!", 1.1f, null));
        shows.add(new TvShow(4, "Темная Башня", "The dark tower", "Один из наиболее показательных " +
                "фильмов, которые дают понять, что книга гораздо лучше.", 4.8f, null));
    }

    @Override
    public Observable<List<TvShow>> getShows() {
        return Observable.just(shows).delay(250, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<TvShow> getShow(int id) {
        return Observable.just(shows.get(id)).delay(50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
