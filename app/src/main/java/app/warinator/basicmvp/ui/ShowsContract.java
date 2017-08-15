package app.warinator.basicmvp.ui;

import java.util.List;

import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.ui.base.MvpPresenter;
import app.warinator.basicmvp.ui.base.MvpView;

/**
 * Shows contract
 */

public interface ShowsContract {

    interface View extends MvpView {
        void displayShows(List<TvShow> shows);

        void displayError(String error);
    }

    interface Presenter extends MvpPresenter<View> {
        void onShowSelected(int showId);
    }
}
