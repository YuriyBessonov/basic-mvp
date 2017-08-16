package app.warinator.basicmvp.data.network;

import app.warinator.basicmvp.data.network.model.TvShowDetails;
import app.warinator.basicmvp.data.network.model.TvShowsPage;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Tv Shows themoviedb API
 */

public interface ShowsAPI {
    String ON_THE_AIR = "tv/on_the_air";
    String SPECIFIC_SHOW = "tv/{id}";
    String POSTER_BASE = "https://image.tmdb.org/t/p/w500/";

    @GET(ON_THE_AIR)
    Observable<TvShowsPage> getShowsOnTheAir(@Query("api_key") String apiKey,
                                             @Query("language") String language,
                                             @Query("page") int page);

    @GET(SPECIFIC_SHOW)
    Observable<TvShowDetails> getSpecificShow(@Path("id") int id, @Query("api_key") String apiKey,
                                              @Query("language") String language);
}
