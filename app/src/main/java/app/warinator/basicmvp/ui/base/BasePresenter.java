package app.warinator.basicmvp.ui.base;

/**
 * Base presenter class
 */

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    protected V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void destroy() {
    }

}
