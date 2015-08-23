package nobugs.team.shopping.mvp.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import nobugs.team.shopping.app.base.LifeCycleCallback;
import nobugs.team.shopping.mvp.view.IView;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public interface IPresenter extends LifeCycleCallback {
    Context getContext();

    Fragment getFragment();

    FragmentActivity getActivity();
}
