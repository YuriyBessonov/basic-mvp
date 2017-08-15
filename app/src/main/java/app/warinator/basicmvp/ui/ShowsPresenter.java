package app.warinator.basicmvp.ui;

import java.util.List;

import app.warinator.basicmvp.data.IDataManager;
import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.ui.base.BasePresenter;
import io.reactivex.functions.Consumer;

/**
 * Shows presenter
 */

public class ShowsPresenter extends BasePresenter<ShowsContract.View> implements ShowsContract.Presenter {

    private IDataManager dataManager;
    private List<TvShow> shows;

    public ShowsPresenter(IDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void viewIsReady() {
        if (shows != null) {
            view.displayShows(shows);
        } else {
            dataManager.getShows()
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.displayError("Cannot receive shows list");
                        }
                    })
                    .subscribe(new Consumer<List<TvShow>>() {
                        @Override
                        public void accept(List<TvShow> tvShows) throws Exception {
                            shows = tvShows;
                            view.displayShows(shows);
                        }
                    });
        }
    }

    @Override
    public void onShowSelected(int showId) {

    }

}
