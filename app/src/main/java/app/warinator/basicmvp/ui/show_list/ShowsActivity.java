package app.warinator.basicmvp.ui.show_list;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import app.warinator.basicmvp.R;
import app.warinator.basicmvp.data.DataManager;
import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.ui.adapter.ShowsAdapter;
import app.warinator.basicmvp.ui.show_details.ShowDetailsActivity;
import app.warinator.basicmvp.utils.NetworkUtils;

public class ShowsActivity extends AppCompatActivity implements ShowsContract.View,
        LoaderManager.LoaderCallbacks<ShowsContract.Presenter> {

    public static final int LOADER_PRESENTER_ID = 101;
    private ShowsContract.Presenter presenter;
    private RecyclerView rvShows;
    private ShowsAdapter showsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        getSupportLoaderManager().initLoader(LOADER_PRESENTER_ID, null, this);
    }

    private void initView() {
        setContentView(R.layout.activity_shows);

        rvShows = findViewById(R.id.rv_shows);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvShows.setLayoutManager(layoutManager);

        showsAdapter = new ShowsAdapter(this, new ShowsAdapter.OnShowClickListener() {
            @Override
            public void onShowClicked(int id) {
                presenter.onShowSelected(id);
            }
        });

        rvShows.setAdapter(showsAdapter);
        rvShows.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
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
    public Loader<ShowsContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new ShowsPresenterLoader(this, new ShowsPresenter(DataManager
                .getInstance(ShowsActivity.this)));
    }

    @Override
    public void onLoadFinished(Loader<ShowsContract.Presenter> loader, ShowsContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void onLoaderReset(Loader<ShowsContract.Presenter> loader) {
    }


    @Override
    public void displayShows(List<TvShow> shows) {
        showsAdapter.updateShows(shows);
    }

    @Override
    public void displayError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToShowDetails(int showId) {
        startActivity(ShowDetailsActivity.newIntent(this, showId));
    }

    @Override
    public void checkConnection() {
        if (!NetworkUtils.isConnectedToNetwork(this)) {
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
