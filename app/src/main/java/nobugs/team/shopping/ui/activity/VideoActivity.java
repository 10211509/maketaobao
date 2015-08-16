package nobugs.team.shopping.ui.activity;

import nobugs.team.shopping.R;
import nobugs.team.shopping.app.base.BaseActivity;
import nobugs.team.shopping.mvp.presenter.IPresenter;

/**
 * Calling Activity
 */
public class VideoActivity extends BaseActivity {
    @Override
    protected IPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video);
    }
}
