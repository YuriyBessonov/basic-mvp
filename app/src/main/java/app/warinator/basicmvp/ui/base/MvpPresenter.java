package app.warinator.basicmvp.ui.base;

/**
 *  Presenter interface
 */

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);
    void detachView();
    void viewIsReady();
    void destroy();

}
