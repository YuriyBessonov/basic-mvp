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
        shows.add(new TvShow(0, "Игра престолов", "Game of Thrones", "К концу подходит время " +
                "благоденствия, и лето, длившееся почти десятилетие, угасает. ", 8.1f, null, 0f));
        shows.add(new TvShow(1, "Форс-мажоры", "Suits", "Убегая после неудачной попытки сбыта наркотиков, " +
                "юрист-самоучка Майк Росс, выдающий себя за выпускника Гарварда, попадает на собеседование " +
                "к одному из лучших адвокатов по сделкам Нью-Йорка Харви Спектру.", 7.4f, null, 1f));
        shows.add(new TvShow(2, "Твин Пикс", "Twin peaks", "История начинается с известия о находке " +
                "обнаженного тела Лоры Палмер, «завернутого в полиэтилен» и выброшенного волнами на " +
                "берег озера. ", 8.0f, null, 3f));
        shows.add(new TvShow(3, "Штамм", "The strain", "Штамм» (англ. The Strain) —американский " +
                "хоррор-сериал про вампиров, созданный по одноименному роману Гильермо Дель Торо и " +
                "Чака Хогана.", 6.5f, null, 6f));
        shows.add(new TvShow(4, "Рик и Морти ", "Rick and Morty", "Учёный-социопат вовлекает своего " +
                "невинного внука в опасные межпространственные приключения.", 8.6f, null, 10f));
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

    @Override
    public Observable<Integer> rateShow(int id, float rating) {
        return Observable.just(0).delay(50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
