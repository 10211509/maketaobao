package nobugs.team.shopping.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by xiayong on 2015/8/7.
 */
public abstract class BaseActivity extends Activity {
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
