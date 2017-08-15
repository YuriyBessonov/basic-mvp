package app.warinator.basicmvp.ui.show_details;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * Shows presenter loader
 */

public class ShowDetailsPresenterLoader extends Loader<ShowDetailsContract.Presenter> {
    private ShowDetailsContract.Presenter presenter;

    public ShowDetailsPresenterLoader(Context context, ShowDetailsContract.Presenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        deliverResult(presenter);
    }
}
