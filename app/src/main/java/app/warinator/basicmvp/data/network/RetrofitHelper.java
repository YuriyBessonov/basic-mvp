package app.warinator.basicmvp.data.network;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit helper
 */

public class RetrofitHelper {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static ShowsAPI showsAPI;

    public static ShowsAPI getShowsAPI() {
        if (showsAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory
                            .createWithScheduler(Schedulers.io()))
                    .build();
            showsAPI = retrofit.create(ShowsAPI.class);

        }
        return showsAPI;
    }
}
