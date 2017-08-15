package app.warinator.basicmvp.ui.show_details;

import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.ui.base.MvpPresenter;
import app.warinator.basicmvp.ui.base.MvpView;

/**
 * Show details contract
 */

public interface ShowDetailsContract {

    interface View extends MvpView {
        void displayShowDetails(TvShow show);

        void displayError(String error);
    }

    interface Presenter extends MvpPresenter<View> {
        void rateShow(float rating);
    }
}
