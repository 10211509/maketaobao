package nobugs.team.shopping.mvp.presenter;

import android.support.v4.app.FragmentActivity;

import nobugs.team.shopping.app.base.LifeCycleCallback;
import nobugs.team.shopping.mvp.view.IView;

/**
 * Autor: wangyf on 2015/8/15 0015 20:45
 * Email: zgtjwyftc@gmail.com
 * Description:
 */
public  class BasePresenter<T extends IView> implements LifeCycleCallback {

    private T mView;

    public void setView(T view) {
        this.mView = view;
    }

    public T getView() {
        return mView;
    }

    public FragmentActivity getContext(){
        if(!(mView instanceof FragmentActivity)){
            throw new ClassCastException("mView must be the instance of the FragmentActivity!");
        }
        return (FragmentActivity) mView;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }
}
