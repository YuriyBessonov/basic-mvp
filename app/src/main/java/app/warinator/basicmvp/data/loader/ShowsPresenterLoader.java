package app.warinator.basicmvp.data.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import app.warinator.basicmvp.ui.ShowsContract;

/**
 * Shows presenter loader
 */

public class ShowsPresenterLoader extends Loader<ShowsContract.Presenter> {
    private ShowsContract.Presenter presenter;

    public ShowsPresenterLoader(Context context, ShowsContract.Presenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        deliverResult(presenter);
    }
}
