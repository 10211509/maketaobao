package nobugs.team.shopping.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import nobugs.team.shopping.mvp.presenter.IPresenter;

/**
 * Created by Administrator on 2015/8/16 0016.
 */
public abstract class BaseFragment<PresenterType extends IPresenter> extends Fragment {


    private PresenterType mPresenter = initPresenter();

    public PresenterType getPresenter() {
        return mPresenter;
    }

    public void setPresenter(PresenterType mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, v);

        setPresenter(initPresenter());
        initView();
        initData();
        initEvent();

        if (mPresenter != null) {
            mPresenter.onCreateView();
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();

        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    protected abstract PresenterType initPresenter();

    protected abstract int getLayoutResId();

    protected void initView(){

    }

    protected void initData() {
    }

    protected void initEvent(){

    }
    protected void updateData() {
    }
}