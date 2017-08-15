package app.warinator.basicmvp.ui.show_list;

import android.content.Context;
import android.support.v4.content.Loader;

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
