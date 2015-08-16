package nobugs.team.shopping.app.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import nobugs.team.shopping.mvp.presenter.BasePresenter;

/**
 * Created by xiayong on 2015/8/7.
 */
public abstract class BaseActivity extends FragmentActivity {

    private BasePresenter<?> mPresenter;

    public BasePresenter<?> getPresenter() {
        return mPresenter;
    }

    public void setPresenter(BasePresenter<?> mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initEvent();
        initData();

        ButterKnife.bind(this);


        if (mPresenter != null){
            mPresenter.onCreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateData();

        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    protected void initView(){};

    protected void initEvent(){};

    protected void initData(){};

    protected void updateData(){};

}
