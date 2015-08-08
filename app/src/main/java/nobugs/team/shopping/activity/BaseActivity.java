package nobugs.team.shopping.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by xiayong on 2015/8/7.
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        initData();
    }
    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

}
