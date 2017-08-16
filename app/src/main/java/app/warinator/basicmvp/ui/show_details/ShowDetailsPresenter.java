package app.warinator.basicmvp.ui.show_details;

import app.warinator.basicmvp.data.IDataManager;
import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.ui.base.BasePresenter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Shows presenter
 */

public class ShowDetailsPresenter extends BasePresenter<ShowDetailsContract.View> implements ShowDetailsContract.Presenter {

    private IDataManager dataManager;
    private TvShow show;
    private Disposable showDisposable;
    private int id;

    public ShowDetailsPresenter(int id, IDataManager dataManager) {
        this.dataManager = dataManager;
        this.id = id;
    }

    @Override
    public void viewIsReady() {
        if (show != null) {
            view.displayShowDetails(show);
        } else {
            showDisposable = dataManager.getShow(id)
                    .subscribe(new Consumer<TvShow>() {
                        @Override
                        public void accept(TvShow tvShow) throws Exception {
                            show = tvShow;
                            view.displayShowDetails(show);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.displayError("Cannot receive specified show");
                        }
                    });
        }
    }

    @Override
    public void rateShow(float rating) {
        dataManager.rateShow(id, rating)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.displayError("Cannot rate show");
                    }
                });
    }

    @Override
    public void destroy() {
        if (showDisposable != null && !showDisposable.isDisposed()) {
            showDisposable.dispose();
        }
    }
}
