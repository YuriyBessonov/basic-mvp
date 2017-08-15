package app.warinator.basicmvp.data.db.model;

/**
 * Tv show model
 */

public class TvShow {

    private int id;

    private String name;

    private String originalName;

    private String overview;

    private float voteAverage;

    private String posterPath;

    public TvShow(int id, String name, String originalName, String overview,
                  float voteAverage, String posterPath) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
    }

}
