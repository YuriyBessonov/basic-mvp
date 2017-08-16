package app.warinator.basicmvp.ui.show_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.warinator.basicmvp.R;
import app.warinator.basicmvp.data.DataManager;
import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.utils.GlideApp;
import app.warinator.basicmvp.utils.NetworkUtils;

public class ShowDetailsActivity extends AppCompatActivity implements ShowDetailsContract.View,
        LoaderManager.LoaderCallbacks<ShowDetailsContract.Presenter> {

    public static final int LOADER_PRESENTER_ID = 101;
    public static final String ARG_SHOW_ID = "show_id";
    public static final float RATING_MULTIPLIER = 2.0f;
    private ShowDetailsContract.Presenter presenter;

    private TextView tvName;
    private TextView tvOrigName;
    private TextView tvRating;
    private RatingBar rbRating;
    private TextView tvDescription;
    private ImageView ivPoster;
    private int showId;


    public static Intent newIntent(Context context, int showId) {
        Intent intent = new Intent(context, ShowDetailsActivity.class);
        intent.putExtra(ARG_SHOW_ID, showId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showId = getIntent().getIntExtra(ARG_SHOW_ID, -1);

        initView();
        getSupportLoaderManager().initLoader(LOADER_PRESENTER_ID, null, this);
    }

    private void initView() {
        setContentView(R.layout.activity_show_details);
        tvName = findViewById(R.id.tv_name);
        tvOrigName =  findViewById(R.id.tv_original_name);
        tvRating = findViewById(R.id.tv_rating);
        tvDescription = findViewById(R.id.tv_description);
        rbRating = findViewById(R.id.rb_rating);
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                presenter.rateShow(v * RATING_MULTIPLIER);
            }
        });
        ivPoster = findViewById(R.id.iv_poster);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    public Loader<ShowDetailsContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new ShowDetailsPresenterLoader(this, new ShowDetailsPresenter(showId,
                DataManager.getInstance(ShowDetailsActivity.this)));
    }

    @Override
    public void onLoadFinished(Loader<ShowDetailsContract.Presenter> loader, ShowDetailsContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void onLoaderReset(Loader<ShowDetailsContract.Presenter> loader) {
    }


    @Override
    public void displayShowDetails(TvShow show) {
        tvName.setText(show.getName());
        tvOrigName.setText(show.getOriginalName());
        tvRating.setText(String.valueOf(show.getVoteAverage()));
        tvDescription.setText(show.getOverview());
        rbRating.setRating(show.getUserRating() / RATING_MULTIPLIER);
        GlideApp.with(this)
                .load(NetworkUtils.getPosterURL(show.getPosterPath()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPoster);
    }

    @Override
    public void displayError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
