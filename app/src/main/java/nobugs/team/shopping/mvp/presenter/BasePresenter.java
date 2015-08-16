package nobugs.team.shopping.mvp.presenter;

import nobugs.team.shopping.app.base.LifeCycleCallback;
import nobugs.team.shopping.mvp.view.IView;

/**
 * Autor: wangyf on 2015/8/15 0015 20:45
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public abstract class BasePresenter<T extends IView> implements LifeCycleCallback {

    private T mView;

    public void setView(T view) {
        this.mView = view;
    }

    public T getView() {
        return mView;
    }
}
